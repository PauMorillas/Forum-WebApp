// --- Variables Globales y Selectores Iniciales ---

const csrfToken = document
  .querySelector('meta[name="_csrf"]')
  .getAttribute("content");
const csrfHeader = document
  .querySelector('meta[name="_csrf_header"]')
  .getAttribute("content");

// --- Referencias a elementos comunes del DOM que se necesitarán en el script ---
// Se inicializarán dentro de DOMContentLoaded para asegurar que existan.
let loginAlert;
let newPostModalElement;
let newPostModal;
let newPostBtn;
let quillInstance; // Instancia real de Quill
let quillFormPost;
let quillHiddenInput;

// --- Funciones auxiliares ---

/**
 * Muestra la alerta de inicio de sesión con un mensaje específico.
 */
function showLoginAlert(message) {
  if (loginAlert) {
    loginAlert.innerHTML = message;
    loginAlert.classList.remove("d-none");
    loginAlert.focus();
  }
}

/**
 * Oculta la alerta de inicio de sesión.
 */
function hideLoginAlert() {
  if (loginAlert && !loginAlert.classList.contains("d-none")) {
    loginAlert.classList.add("d-none");
  }
}

/**
 * Maneja la animación de un icono (cambio de clase y animación).
 */
function animateIconToggle(icon) {
  icon.classList.toggle("fa-regular");
  icon.classList.toggle("fa-solid");
  icon.classList.add("icon-animate");
  icon.addEventListener(
    "animationend",
    function handler() {
      icon.classList.remove("icon-animate");
      icon.removeEventListener("animationend", handler);
    },
    { once: true }
  );
}

// --- Lógica Principal al Cargar el DOM ---
document.addEventListener("DOMContentLoaded", () => {
  // 1. Inicialización de Elementos del DOM
  loginAlert = document.getElementById("loginAlert");
  newPostBtn = document.getElementById("btnNewPost");
  newPostModalElement = document.getElementById("newPostModal");
  const quillContainer = document.getElementById("quill-editor");
  quillFormPost = document.getElementById("formPost");
  quillHiddenInput = document.getElementById("hiddenContent");
  const btnSubmit = document.getElementById("btnSubmit");

  // Inicializar el modal de Bootstrap
  if (newPostModalElement) {
    newPostModal = new bootstrap.Modal(newPostModalElement);
  }

  // 2. Lógica para el Editor Quill (si lo hay en la página)
  if (quillContainer) {
    const quill = new Quill("#quill-editor", {
      theme: "snow",
    });

    if (quillFormPost) {
      quillFormPost.addEventListener("submit", function (e) {
        if (quillContainer) {
          const content = quill.getSemanticHTML();
          if (!content || content === "<p><br></p>") {
            e.preventDefault(); // evita enviar el formulario vacío
            return;
          }

          quillHiddenInput.value = content;
        } else {
          e.preventDefault();
          console.error("Editor Quill no inicializado.");
        }
      });
    }
  }

  // 3. Lógica para el botón "New Post" y la validación de usuario
  if (newPostBtn) {
    newPostBtn.addEventListener("click", (e) => {
      if (!window.hayUsuario) {
        e.preventDefault(); // Evita que Bootstrap intente abrir el modal
        showLoginAlert(`
                    You should sign in before posting.
                    <a href="/login" class="alert-link"> Sign in here</a> ; ]
                `);
      } else {
        newPostModal.show(); // Abre el modal solo si hay usuario
      }
    });
  }

  // 4. Manejo de la visibilidad de la alerta de login
  // Oculta el alert de login si se hace clic fuera de él
  document.body.addEventListener("click", (e) => {
    // Si la alerta está visible y el clic no fue dentro de la alerta misma
    // Y el clic no fue en el botón "New Post" (ya que tiene su propia lógica)
    // Y el clic NO fue dentro de un contenedor de like o comentario (para no ocultarla inmediatamente)
    if (
      loginAlert &&
      !loginAlert.classList.contains("d-none") &&
      !loginAlert.contains(e.target) &&
      e.target !== newPostBtn &&
      !e.target.closest(".like-container") && // Excluye clics en el botón de like
      !e.target.closest(".comment-container") // Excluye clics en el botón de comentario
    ) {
      hideLoginAlert();
    }
  });

  // Oculta la alerta de login si el modal de newPost se muestra
  if (newPostModalElement) {
    newPostModalElement.addEventListener("show.bs.modal", hideLoginAlert);
  }

  // 5. Ocultar el toast de bienvenida
  const toastEl = document.getElementById("welcomeToast");
  const wrapper = document.getElementById("toastWrapper");

  if (toastEl && wrapper) {
    wrapper.removeAttribute("hidden");
    const toast = new bootstrap.Toast(toastEl, { delay: 4000 });

    toastEl.addEventListener("hide.bs.toast", (event) => {
      event.preventDefault();
      toastEl.classList.add("fade-out");
      setTimeout(() => {
        toastEl.classList.remove("fade-out");
        toast.hide();
        wrapper.setAttribute("hidden", "");
      }, 1000);
    });
    toast.show();
  }

  // 6. Enfoque en el textarea de comentarios al cargar la página de post-info
  if (window.location.hash === "#comment-textarea") {
    const textarea = document.getElementById("comment-textarea");
    if (textarea) {
      textarea.focus();
    }
  }
});

// --- Funciones de Interacción (Likes y Comentarios) ---
// Estas funciones se llaman directamente desde el HTML usando th:onclick.

/**
 * Cambia el estado de like de un post y actualiza la UI.
 * @param {number} postId El ID del post.
 * @param {HTMLElement} button El botón del like (this).
 */
function toggleLike(postId, button) {
  if (!window.hayUsuario) {
    showLoginAlert(`
            You should sign in before liking a post.
            <a href="/login" class="alert-link">Sign in here</a> ; ]
        `);
    button.classList.add("btn-no-action"); // Aplica la clase si no hay usuario
    return; // Salimos de la función sin hacer la llamada API
  }

  // Animación inmediata al hacer clic (solo si hay usuario y la lógica procede)
  animateIconToggle(button.querySelector("i"));

  fetch("/likes/toggle", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [csrfHeader]: csrfToken,
    },
    body: JSON.stringify({ postId: postId }),
  })
    .then((res) => {
      if (!res.ok) {
        // Si la API falla, revertimos la animación del icono
        const icon = button.querySelector("i");
        icon.classList.toggle("fa-regular");
        icon.classList.toggle("fa-solid");
        throw new Error(`HTTP ${res.status}`);
      }
      return res.json();
    })
    .then((data) => {
      const countSpan = button.querySelector("span");
      countSpan.textContent = data.totalLikes;
    })
    .catch((err) => console.error("Error al hacer like:", err));
}

/**
 * Redirige a la vista del post y enfoca el área de comentarios.
 * @param {number} postId El ID del post.
 * @param {HTMLElement} button El botón del comentario (this). <--- ¡Nuevo parámetro!
 */
function goToPostAndFocusComment(postId, button) {
  if (!window.hayUsuario) {
    showLoginAlert(`
            You should sign in before commenting on a post.
            <a href="/login" class="alert-link">Sign in here</a> ; ]
        `);
    // Ahora sí podemos aplicar la clase porque recibimos el botón
    button.classList.add("btn-no-action");
    return; // Salimos de la función si no hay usuario
  }
  // Si hay usuario, redirige
  window.location.href = `/posts/${postId}#comment-textarea`;
}
