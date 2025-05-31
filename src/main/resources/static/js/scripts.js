let btnsLike = document.getElementsByClassName("like-container");
let btnsComment = document.getElementsByClassName("comment-container");

Array.from(btnsLike).forEach((btn) => {
  btn.addEventListener("click", function () {
    const icon = btn.querySelector("i");

    icon.classList.toggle("fa-regular");
    icon.classList.toggle("fa-solid");

    // Clase con la animación
    icon.classList.add("icon-animate");

    icon.addEventListener("animationend", function handler() {
      icon.classList.remove("icon-animate");
      icon.removeEventListener("animationend", handler); // Evitamos duplicación
    });
  });
});

Array.from(btnsComment).forEach((btn) => {
  btn.addEventListener("click", function () {
    const icon = btn.querySelector("i");

    icon.classList.toggle("fa-regular");
    icon.classList.toggle("fa-solid");

    icon.classList.add("icon-animate");

    icon.addEventListener("animationend", function handler() {
      icon.classList.remove("icon-animate");
      icon.removeEventListener("animationend", handler);
    });
  });
});
