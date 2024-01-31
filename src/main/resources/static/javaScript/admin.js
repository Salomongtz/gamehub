const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            games: [],
            selectedGame: null,
            title: "",
            description: "",
            developer: "",
            imageURL: "",
            sales: 0,
            price: 0,
            releaseDate: "",
            discount: 0,
            ratingOptions: ["E", "E10PLUS", "T", "M", "AO", "RP", "RP17", "NOTRATED"],
            rating: "",
            genreOptions: ["ACTION", "ADVENTURE", "FANTASY", "FIGHTING", "HORROR", "PUZZLE", "RACING", "ROLE_PLAYING", "SHOOTER", "SIMULATION", "SPORTS", "STRATEGY", "SURVIVAL"],       // Opciones de géneros desde el backend
            selectedGenres: [],
            platformOptions: ["PC", "PS4", "PS5", "SWTICH", "XBOX"],
            selectedPlatforms: [],
            currentForm: 'deleteGame',
            selectedGameId: null,
            editTitle:"",
            editDescription:"",
            editImageURL:"",
            editPrice:"",
            editSelectedGenres:"",
            editSelectedPlatforms:"",
            editReleaseDate:"",
            editSales:"",
            editRating:"",
            editDeveloper:"",
            editDiscount:"",
        };
    },

    created() {
        this.loadData();
    },

    methods: {
        loadData() {
            axios.get("/api/games")
                .then((response) => {
                    this.games = response.data;
                    console.log(this.games);
                })
                .catch((error) => console.error(error));
        },

        toggleForm() {
            if (this.currentForm === 'deleteGame') {
                this.currentForm = 'createGameForm';
            } else if (this.currentForm === 'createGameForm') {
                this.currentForm = 'editGameForm';
            } else {
                this.currentForm = 'deleteGame';
            }
        },

        createGame() {
            const gameData = {
                title: this.title,
                description: this.description,
                developer: this.developer,
                imageURL: this.imageURL,
                sales: this.sales,
                price: this.price,
                releaseDate: this.releaseDate,
                discount: this.discount,
                rating: this.rating,
                genres: this.selectedGenres,  // Utiliza selectedGenres en lugar de genres
                platforms: this.selectedPlatforms  // Utiliza selectedPlatforms en lugar de platforms
            };

            axios.post("/api/games", gameData)
                .then((response) => {
                    console.log(response.data);
                    this.loadData();
                })
                .catch((error) => console.error(error));
        },

        deleteGame(id) {
            axios.delete(`/api/games/${id}`)
                .then((response) => {
                    console.log(response.data);
                    this.loadData();
                })
                .catch((error) => console.error(error));
        },

        dataGame() {
            console.log(this.selectedGame) 
                    this.editTitle = this.selectedGame.title;
                    this.editDescription = this.selectedGame.description;
                    this.editDeveloper = this.selectedGame.developer;
                    this.editImageURL = this.selectedGame.image;
                    this.editSales = this.selectedGame.sales;
                    this.editPrice = this.selectedGame.price;
                    this.editReleaseDate = this.selectedGame.date;
                    this.editDiscount = this.selectedGame.discount;
                    this.editRating = this.selectedGame.rating;
                    this.editSelectedGenres = this.selectedGame.genre;
                    this.editSelectedPlatforms = this.selectedGame.platforms;
                    console.log(this.editTitle)
                    console.log(this.editDescription)
        },

        editGame(id){
            axios.patch(`/api/games/${id}`,gameEdit)
            .then()
        }

    }
});

app.mount("#app");