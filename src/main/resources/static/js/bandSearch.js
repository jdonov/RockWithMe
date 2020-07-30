window.addEventListener('load', () => {
    const localHost = "http://localhost:8080";
    const nameInput = document.getElementById("name");
    const styleInput = document.getElementById("style");
    const goalInput = document.getElementById("goal");
    const townInput = document.getElementById("town");
    const needMembersInput = document.getElementById("needMembers");
    const needsProducerInput = document.getElementById("needsProducer");
    const divContainer = document.getElementById("search-container");
    const divList = document.getElementById("pagination");
    document.getElementById("btnSearch").addEventListener('click', searchBands);
    let sObj = {};
    let size = 2;

    async function searchBands(e) {
        e.preventDefault();
        searchObj();
        while (divList.firstChild) {
            divList.removeChild(divList.firstChild);
        }
        let pages = await loadData(1, size);
        console.log(pages);
        if (pages > 0) {
            await renderList(pages);
        }
    }

    async function loadData(page, size) {
        let response = await searchSend(sObj, page, size);
        while (divContainer.firstChild) {
            divContainer.removeChild(divContainer.firstChild);
        }
        console.log(response);
        if (response.content.length !== 0) {
            response.content.forEach(b => {
                divContainer.appendChild(renderBand(b));
            });
            return Number(response.totalPages);
        } else {
            let h = document.createElement("h3");
            h.setAttribute("id", "no-bands-message")
            h.textContent = "No bands found!";
            divContainer.appendChild(h);
            return 0;
        }
    }

    function searchObj() {
        const search =  {
            name: nameInput.value,
            style: styleInput.value,
            goal: goalInput.value,
            town: townInput.value,
            needMembers: needMembersInput.value,
            needsProducer: needsProducerInput.value
        }
        sObj = Object.fromEntries(Object.entries(search).filter(([key, value]) => value));
    }

    function renderList(totalPages) {
        let ulList = document.createElement("ul");
        for (let i = 1; i <= totalPages; i++) {
            let li = document.createElement("li");
            let btn = document.createElement("button");
            btn.textContent = i;
            btn.addEventListener("click", searchBandsPages);
            li.appendChild(btn);
            ulList.appendChild(li);

            async function searchBandsPages(e) {
                e.preventDefault();
                let r = await loadData(i, size);
                console.log(r);
            }
        }
        divList.appendChild(ulList);
    }

    async function searchSend(searchObj, page, size) {
        const response = await fetch(`${localHost}/api/bands/search?page=${page}&size=${size}`, {
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