const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            navMenu: false,
            games: [],
            filteredGames: [],
            genres: [],
            platforms: [],
            price: [],
        };
    },
    created() {
        this.loadData();
    },
    methods: {
        loadData() {
            axios.get("/api/games")
                .then(response => {
                    this.games = response.data;
                    console.log("Games", this.games);
                    this.filteredGames = response.data;
                    console.log("GamesFiltered", this.filteredGames);
                    this.genres = [...new Set(response.data.flatMap(data => data.genre))].sort();
                    console.log("Generos", this.genres);
                    this.platforms = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("plataformas", this.platforms);
                    this.price = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("Precios", this.price);
                })
                .catch(error => {
                    if (error.response) {
                        Swal.fire({
                            background: "black",
                            color: "yellow",
                            icon: 'error',
                            title: 'Dear gamer, we must inform you:',
                            text: `${JSON.stringify(error.response.data, null, 2)}`,
                            footer: `Error de respuesta: ${error.response.status}`
                        });
                    }
                });
        },
    }
}).mount("#app");

// Aquí quitamos el código que estaba dentro del método "created"
// var header = document.getElementById('myHeader');
// var page = document.getElementById('page');
// var openMenuButton = document.getElementById('openmenu');

// window.addEventListener('scroll', function () {
//     page.classList.remove('menuopen');
//     if (window.scrollY >= 100) {
//         header.classList.add('sticky');
//     } else {
//         header.classList.remove('sticky');
//     }
// });

// Event listener to remove the sticky class when the button is clicked
// openMenuButton.addEventListener('click', function () {
//     header.classList.remove('sticky');
//     page.classList.add('menuopen');
// });

var links = document.querySelectorAll('a[href^="#"]');

links.forEach(function (link) {
    link.addEventListener('click', function (event) {
        // Prevent the default action
        event.preventDefault();

        // Get the target element
        var targetId = this.getAttribute('href');
        var targetElement = document.querySelector(targetId);

        // Smooth scroll to target
        if (targetElement) {
            targetElement.scrollIntoView({
                behavior: 'smooth'
            });
        }
    });
});
