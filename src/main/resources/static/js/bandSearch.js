window.addEventListener('load', () => {
    const localHost = "http://localhost:8080";
    const nameInput = document.getElementById("name");
    const styleInput = document.getElementById("style");
    const goalInput = document.getElementById("goal");
    const townInput = document.getElementById("town");
    const needMembersInput = document.getElementById("needMembers");
    const needsProducerInput = document.getElementById("needsProducer");
    const divContainer = document.getElementById("search-container");
    document.getElementById("btnSearch").addEventListener('click', searchBands);

    async function searchBands(e) {
        while (divContainer.firstChild) {
            divContainer.removeChild(divContainer.firstChild);
        }
        e.preventDefault();
        const search = {
            name: nameInput.value,
            style: styleInput.value,
            goal: goalInput.value,
            town: townInput.value,
            needMembers: needMembersInput.value,
            needsProducer: needsProducerInput.value
        }
        let newSearch = Object.fromEntries(Object.entries(search).filter(([key, value]) => value));
            let response = await searchSend(newSearch);

        if (response.content.length !== 0) {
            response.content.forEach(b => {
                divContainer.appendChild(renderBand(b));
            });
        } else {
            let h = document.createElement("h3");
            h.setAttribute("id", "no-bands-message")
            h.textContent = "No bands found!";
            divContainer.appendChild(h);
        }
    }

    async function searchSend(searchObj) {
        const response = await fetch(`${localHost}/api/bands/search?page=1&size=2`, {
            method: "POST",
            body: JSON.stringify(searchObj),
            headers: {
                'Content-Type': 'application/json'
            }
        });
        return await response.json();
    }

    function renderBand(band) {
        let divBand = document.createElement("div");
        let linkBand = document.createElement("a");
        linkBand.setAttribute("href", `${localHost}/bands/details/${band.id}`);
        let spanName = document.createElement("span");
        spanName.textContent = `Band name: ${band.name}`;
        linkBand.appendChild(spanName);
        divBand.appendChild(linkBand);
        band.styles.forEach(s => {
            let p = document.createElement("p");
            p.textContent = s;
            linkBand.appendChild(p);
        });
        band.goals.forEach(g => {
            let p = document.createElement("p");
            p.textContent = g;
            linkBand.appendChild(p);
        });
        return divBand;
    }
})