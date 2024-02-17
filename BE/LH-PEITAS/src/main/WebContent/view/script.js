class uniformes{
    constructor(equipe, link, temporada, uniforme){
        this.equipe = equipe;
        this.link = link;
        this.temporada = temporada;
        this.uniforme = uniforme;
    }
}

function getService(URL, seletor){  
    let options = {
            mode: 'cors',
            method: 'GET',
            headers: {
                "Accept": "application/json",
                "Content-Type": "application/json;charset=utf-8"
                }
            };

    fetch(`${URL}`,options)
        .then((resp) => resp.json())
        .then((data) => criarList(data,seletor))
        .catch(e =>
            console.log(e.message))
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
//Inicializar 
request('http://localhost:8088/LH-PEITAS/base', options);
getService("http://localhost:8088/LH-PEITAS/listUniformes/cores", "#filtro-listCor-ul");
getService("http://localhost:8088/LH-PEITAS/listarTimes/ligas", "#filtro-listLiga-ul");

// Criar
function criarList(list, seletor){
    const filtroUl = document.querySelector(`${seletor}`);
    list.forEach(
        item =>{
            let cor = document.createElement('li');
            filtroUl.appendChild(cor).innerText = item;
            console.log(item);
        }
        )
        console.log("rodou function");
    }

function criarCard(array){
    array.forEach(element => {
        // clubes = new uniformes(element.nome, element.link, element.temporada, element.uniforme);
        // console.log(clubes);
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
// End Criar

// Skeleton
const allSkeleton = document.querySelectorAll('.skeleton');
	window.addEventListener('load', ()=>{
		allSkeleton.forEach(item=>{
			item.classList.remove('skeleton')
		})
	})
// End Skeleton

// Esconder list
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
//

// Buscar por Time
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
//

const filtroCor = document.querySelector('#filtro-listCor');
filtroCor.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listCor-ul')
    if(list.getAttribute('hidden') == "false"){
        list.setAttribute('style','display:none')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        list.setAttribute('style','display:block')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false')
        
    }
});

const filtroLiga = document.querySelector('#filtro-listLiga');
filtroLiga.addEventListener('click', ()=>{
    let list = document.querySelector('#filtro-listLiga-ul')
    if(list.getAttribute('hidden') == "false"){
        list.setAttribute('style','display:none')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','true')
    }else{
        list.setAttribute('style','display:block')
        list.removeAttribute('hidden');
        list.setAttribute('hidden','false')
        
    }
});
