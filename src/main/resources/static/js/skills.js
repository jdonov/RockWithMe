const divSkills = document.getElementById("add-skills");
const btnAdd = document.getElementById("addSkills");
btnAdd.addEventListener('click', addSkills);

function addSkills() {
    divSkills.classList.remove("hidden");
}
