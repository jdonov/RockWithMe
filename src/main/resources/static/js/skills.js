//skills
const divSkills = document.getElementById("add-skills");
const btnAdd = document.getElementById("addSkills");
btnAdd.addEventListener('click', addSkills);

function addSkills() {
    divSkills.classList.remove("hidden");
}

// const btnSubmit = document.getElementById("prevent");
// btnSubmit.addEventListener('click', prevent);
// function prevent(e){
//     e.preventDefault();
// }