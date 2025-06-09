const btnsLike = document.getElementsByClassName("like-container");
const btnsComment = document.getElementsByClassName("comment-container");

const csrfToken = document
  .querySelector('meta[name="_csrf"]')
  .getAttribute("content");
const csrfHeader = document
  .querySelector('meta[name="_csrf_header"]')
  .getAttribute("content");

const quillContainer = document.getElementById("quill-editor");

if (quillContainer) {
  const quill = new Quill("#quill-editor", {
    theme: "snow",
  });

  const formPost = document.getElementById("formPost");
  const hiddenInput = document.getElementById("hiddenContent");

  if (formPost && hiddenInput) {
    formPost.addEventListener("submit", function () {
      hiddenInput.value = quill.root.innerHTML; // Copiamos el HTML en ese campo
    });
  }
}

Array.from(btnsLike).forEach((btn) => {
  btn.addEventListener("click", function () {
    if (!window.hayUsuario) {
      btn.classList.add("btn-no-action");
    } else {
      const icon = btn.querySelector("i");

      icon.classList.toggle("fa-regular");
      icon.classList.toggle("fa-solid");

      // Clase con la animación
      icon.classList.add("icon-animate");

      icon.addEventListener("animationend", function handler() {
        icon.classList.remove("icon-animate");
        icon.removeEventListener("animationend", handler); // Evitamos duplicación
      });
    }
  });
});

Array.from(btnsComment).forEach((btn) => {
  btn.addEventListener("click", function () {
    if (!window.hayUsuario) {
      const loginAlert = document.getElementById("loginAlert");
      btn.classList.add("btn-no-action");
      loginAlert.classList.remove("d-none");
      loginAlert.innerHTML = `
  You should sign in before comment a post.
  <a href="/login" class="alert-link">Sign in here</a> ; ]
`;
      loginAlert.focus();
    } else {
      const icon = btn.querySelector("i");

      icon.classList.toggle("fa-regular");
      icon.classList.toggle("fa-solid");

      icon.classList.add("icon-animate");

      icon.addEventListener("animationend", function handler() {
        icon.classList.remove("icon-animate");
        icon.removeEventListener("animationend", handler);
      });
    }
  });
});

// Oculta el toast de bienvenida

document.addEventListener("DOMContentLoaded", () => {
  const toastEl = document.getElementById("welcomeToast");
  const wrapper = document.getElementById("toastWrapper");

  if (toastEl && wrapper) {
    wrapper.removeAttribute("hidden");

    const toast = new bootstrap.Toast(toastEl, { delay: 4000 });

    toastEl.addEventListener("hide.bs.toast", (event) => {
      event.preventDefault(); // Interceptamos
      toastEl.classList.add("fade-out");

      setTimeout(() => {
        toastEl.classList.remove("fade-out");
        toast.hide();
        wrapper.setAttribute("hidden", ""); // Lo ocultamos del DOM tras ocultarse
      }, 1000);
    });

    toast.show();
  }
});

// Muestra el alert del login dinámicamente
document.addEventListener("DOMContentLoaded", () => {
  const loginAlert = document.getElementById("loginAlert");
  if (!loginAlert) return;

  // Guardamos el contenido original para poder restaurarlo después
  const originalLoginAlertHTML = loginAlert.innerHTML;

  // Cuando se haga click en new post
  const newPostBtn = document.querySelector('[data-bs-target="#newPostModal"]');
  if (newPostBtn && !window.hayUsuario) {
    newPostBtn.addEventListener("click", (e) => {
      e.preventDefault();
      loginAlert.innerHTML = originalLoginAlertHTML; // Restauramos contenido original
      loginAlert.classList.remove("d-none");
      loginAlert.classList.add("show");
    });
  }
});

// Cambia el estado de un boton según si se le dio like al post
function toggleLike(postId, button) {
  const loginAlert = document.getElementById("loginAlert");
  if (!window.hayUsuario) {
    loginAlert.classList.remove("d-none");
    loginAlert.innerHTML = `
  You should sign in before liking a post.
  <a href="/login" class="alert-link">Sign in here</a> ; ]
`;
    loginAlert.focus();

    return; // Salimos de la funcion sin hacer nada más
  }

  fetch("/likes/toggle", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      [csrfHeader]: csrfToken,
    },
    body: JSON.stringify({ postId: postId }),
  })
    .then((res) => {
      if (!res.ok) throw new Error(`HTTP ${res.status}`);
      return res.json();
    })
    .then((data) => {
      const icon = button.querySelector("i");
      const countSpan = button.querySelector("span");

      if (data.liked) {
        icon.classList.remove("fa-regular");
        icon.classList.add("fa-solid");
      } else {
        icon.classList.remove("fa-solid");
        icon.classList.add("fa-regular");
      }

      countSpan.textContent = data.totalLikes;
    })
    .catch((err) => console.error("Error al hacer like:", err));
}

// Para redirigir a la view del post cuando se le de a comment
function goToPostAndFocusComment(postId) {
  if (hayUsuario) {
    // Redirige a la vista del post
    window.location.href = "/posts/" + postId + "#comment-textarea";
  }
}

window.addEventListener("DOMContentLoaded", () => {
  if (window.location.hash === "#comentario-textarea") {
    const textarea = document.getElementById("comentario-textarea");
    if (textarea) {
      textarea.focus();
    }
  }
});
