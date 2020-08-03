const divSkills = document.getElementById("join");
const btnAdd = document.getElementById("join-band");
let trackChange = true;
if (btnAdd) {
    btnAdd.addEventListener('click', show);
}


function show() {
    if(trackChange) {
        divSkills.classList.remove("hidden");
        trackChange = false;
        btnAdd.textContent = "Cancel request";
        divSkills.scrollIntoView(true);
    } else {
        divSkills.classList.add("hidden");
        trackChange = true;
        btnAdd.textContent = "Join the band";
    }
}