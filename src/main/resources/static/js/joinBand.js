const divSkills = document.getElementById("join");
const btnAdd = document.getElementById("join-band");
btnAdd.addEventListener('click', show);

function show() {
    divSkills.classList.remove("hidden");
}