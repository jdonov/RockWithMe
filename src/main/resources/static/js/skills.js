const divSkills = document.getElementById("add-skills");
const btnAdd = document.getElementById("addSkills");
let trackChange = true;
btnAdd.addEventListener('click', addSkills);

function addSkills() {
    if(trackChange) {
        divSkills.classList.remove("hidden");
        trackChange = false;
        btnAdd.textContent = "Hide";
    } else {
        divSkills.classList.add("hidden");
        trackChange = true;
        btnAdd.textContent = "Add new skills";
    }
}
