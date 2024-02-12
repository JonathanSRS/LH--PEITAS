class uniformes{
    constructor(equipe, link, temporada, uniforme){
        this.equipe = equipe;
        this.link = link;
        this.temporada = temporada;
        this.uniforme = uniforme;
    }
}

function request(URL, options){
    fetch(`${URL}`,options)
    .then(resp =>resp.json())
    .then(data => {
        criarCard(data)
    })
    .catch(e =>
        console.log(e.message))
}


const options = {
    mode: 'cors',
    method: 'POST',
    headers: {
        "Accept": "application/json",
        "Content-Type": "application/json;charset=utf-8"
    },
    body: JSON.stringify({time:''})
    };


request('http://localhost:8088/LH-PEITAS/base', options);

function criarCard(array){
    array.forEach(element => {
        // clubes = new uniformes(element);
        let idList = document.querySelector('#list');
        let card = document.createElement('div');
        let equipeDiv = document.createElement('div');
        let temporadaDiv = document.createElement('div');
        let corDiv = document.createElement('div');
        let imagem = document.createElement('img');
        let equipe = document.createElement('span');
        let temporada = document.createElement('span');
        let cor = document.createElement('span');
        
        equipeDiv.appendChild(equipe).innerText = 'Equipe: ' +element.nome.replace(element.nome[0], element.nome[0].toUpperCase());
        temporadaDiv.appendChild(temporada).innerText = 'Temporada: '+ element.temporada.substring(0,4);
        corDiv.appendChild(cor).innerText = 'Nome: '+ element.uniforme;
        card.appendChild(imagem).setAttribute('src', element.link);
        card.appendChild(equipeDiv);
        card.appendChild(temporadaDiv);
        card.appendChild(corDiv);
        card.setAttribute('class','card skeleton');
        idList.appendChild(card);
        function skeleton(){
            const allSkeleton = document.querySelectorAll('.skeleton');
            allSkeleton.forEach(item=>{
                item.classList.remove('skeleton')
            })}
        skeleton();
    });
}
const allSkeleton = document.querySelectorAll('.skeleton');
	window.addEventListener('load', ()=>{
		allSkeleton.forEach(item=>{
			item.classList.remove('skeleton')
		})
	})
const filtro = document.getElementById('filtro-icon');
filtro.addEventListener("click", ()=>{
    let list = document.querySelector('.filtro-list')
    if(list.getAttribute('hidden') == "false"){
        list.setAttribute('style','display:none')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        list.setAttribute('style','display:flex')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false')

    }
})

const navSearch = document.querySelector('#nav-search');
navSearch.addEventListener('input' , (e)=>{
    let text = e.target.value;

    const cards = document.querySelectorAll(".card");
    cards.forEach(item =>{
        item.remove();
    })

    let options = {
        mode: 'cors',
        method: 'POST',
        headers: {
            "Accept": "application/json",
            "Content-Type": "application/json;charset=utf-8"
        },
        body: JSON.stringify({time:text})
        };
    request('http://localhost:8088/LH-PEITAS/base', options);
})