const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            modalFilter:false,

            navMenu: false,
            games: [],
            filteredGames: [],
            selectGenre: "",
            selectPlatform: "",
            search: "",
            games: [],
            genres: [],
            platforms: [],
            price: [],
            modalOpen: false,
            selectedGame: [],
            filteredGames: [],
            selectedPriceRange: 0,
        };
    },
    created() {
        this.loadData()
    },

    methods: {
        loadData() {
            axios.get("/api/games")
                .then(response => {
                    this.games = response.data
                    console.log("Games", this.games)
                    this.filteredGames = response.data
                    console.log("GamesFiltered", this.filteredGames)
                    this.genres = [...new Set(response.data.flatMap(data => data.genre))].sort();
                    console.log("Generos", this.genres)
                    this.platforms = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("plataformas", this.platforms)
                    this.price = [...new Set(response.data.flatMap(data => data.platforms))].sort();
                    console.log("Precios", this.price)

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

        toggleFlip(game) {
            // Alternar la propiedad flipped de la tarjeta
            game.flipped = !game.flipped;
        },


        filterGenre(event) {
            this.selectGenre = event.target.value;
            console.log("Selected Genre", this.selectGenre);
            this.filterCrossSearch();
        },

        filterPlatforms(event) {
            this.selectPlatform = event.target.value;
            console.log("Selected Platform", this.selectPlatform);
            this.filterCrossSearch();
        },

        filterTitle(event) {
            this.search = event.target.value;
            console.log("Selected Title", this.search);
            this.filterCrossSearch();
        },

        filterPrice(event) {
            this.selectedPirce = event.target.value;
            console.log("Selected Price", this.selectedPirce);
            this.filterCrossSearch();
        },

        filterDate(event) {
            this.selectedDate = event.target.value;
            console.log("Selected Date", this.selectedDate);
            this.filterCrossSearch();
        },

        // Método para ordenar los juegos por precio de menor a mayor
        sortGamesByPriceAsc() {
            this.filteredGames.sort((a, b) => a.price - b.price);
        },

        // Método para ordenar los juegos por precio de mayor a menor
        sortGamesByPriceDesc() {
            this.filteredGames.sort((a, b) => b.price - a.price);
        },

        sortGamesByDateAsc() {
            this.filteredGames.sort((a, b) => {
                const dateA = new Date(a.date);
                const dateB = new Date(b.date);
                return dateA - dateB;
            });
        },
        
        sortGamesByDateDesc() {
            this.filteredGames.sort((a, b) => {
                const dateA = new Date(a.date);
                const dateB = new Date(b.date);
                return dateB - dateA;
            });
        },


        filterCrossSearch() {
            console.log("Cross Search:", this.search);
            console.log("Cross platform:", this.selectPlatform);
            console.log("Cross genre:", this.selectGenre);
            console.log("Cross price:", this.selectedPirce);

            const aux = this.games.filter(game =>
                (!this.search || (game.title && game.title.toLowerCase().includes(this.search.toLowerCase()))) &&
                (!this.selectPlatform || this.selectPlatform === 'all' || (game.platforms && game.platforms.some(platform => platform.toLowerCase().includes(this.selectPlatform.toLowerCase())))) &&
                (!this.selectGenre || this.selectGenre === 'all' || (game.genre && game.genre.some(genre => genre.toLowerCase().includes(this.selectGenre.toLowerCase())))) &&
                (!this.selectedPirce || game.price <= this.selectedPirce) &&
                (!this.selectedDate || game.date <= this.selectedDate)
            );

            this.sortGamesByPriceAsc();
            this.sortGamesByPriceDesc();
            this.sortGamesByDateAsc();
            this.sortGamesByDateDesc();

            this.filteredGames = aux;
            console.log("Filtered Games", this.filteredGames);
        },

        openModal(game) {
            this.selectedGame = game
            this.modalOpen = true
            console.log(this.selectedGame)
        },

        closeModal() {
            this.modalOpen = false
            console.log(this.modalOpen)

        },

        showMenu() {
            if (this.navMenu == false) {
                this.navMenu = true
            }

            else {
                this.navMenu = false
            }
        },

        filterMenu(){
            this.modalFilter = !this.modalFilter
        }
    }


}).mount("#app");