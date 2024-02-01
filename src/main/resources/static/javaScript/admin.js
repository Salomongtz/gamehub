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
            stock:"",
            sales: 0,
            price: 0,
            pulbisher:"",
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
            editSelectedGenres:[],
            editSelectedPlatforms:[],
            editReleaseDate:"",
            editSales:"",
            editRating:"",
            editDeveloper:"",
            editDiscount:"",
            editStock:"",
            editPublisher:""
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
                    this.cart = JSON.parse(localStorage.getItem("cart")) || []
                })
                .catch((error) => console.error(error));
        },

        toggleForm(event) {

            const deleteButton = document.querySelector(".delete");
            const createButton = document.querySelector(".create");
            const updateButton = document.querySelector(".update");

            if (event.target == createButton) {
                this.currentForm = 'createGameForm';
            } else if (event.target == updateButton) {
                this.currentForm = 'editGameForm';
            } else {
                this.currentForm = 'deleteGame';
            }
        },logout() {
            axios.post("/api/logout")
              .then(response => {
                console.log(response)
                this.customer = null
                location.reload();
                localStorage.clear();
              })
              .catch(error => console.log("Error", error))
          },

        createGame() {
            const gameData = {
                title: this.title,
                description: this.description,
                developer: this.developer,
                imageURL: this.imageURL,
                sales: this.sales,
                price: this.price,
                publisher: this.publisher,
                stock: this.stock,
                releaseDate: this.releaseDate,
                discount: this.discount,
                rating: this.rating,
                genres: this.selectedGenres,
                platforms: this.selectedPlatforms
            };
        
            axios.post("/api/games", gameData)
                .then(() => {
                    this.loadData();
                    this.clearFields();
                    Swal.fire({
                        position: "top-end",
                        icon: "success",
                        title: "Your work has been saved",
                        showConfirmButton: false,
                        timer: 1500
                    });
                })
                .catch((error) => {
                    console.error(error);
                    Swal.fire({
                        icon: "error",
                        title: "Oops...",
                        text: "Something went wrong!",
                    });
                });
        },

        deleteGame(id) {
            Swal.fire({
                title: "Are you sure?",
                text: "You won't be able to revert this!",
                icon: "warning",
                showCancelButton: true,
                confirmButtonColor: "#3085d6",
                cancelButtonColor: "#d33",
                confirmButtonText: "Yes, delete it!"
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.delete(`/api/games/${id}`)
                        .then(() => {
                            this.loadData();
                            this.clearFields();
                            Swal.fire({
                                title: "Deleted!",
                                text: "Your file has been deleted.",
                                icon: "success"
                            });
                        })
                        .catch((error) => {
                            console.error(error);
                            Swal.fire({
                                title: "Error",
                                text: "An error occurred while deleting the game.",
                                icon: "error"
                            });
                        });
                }
            });
        },
        

        dataGame() {
            console.log(this.selectedGame);
            this.editTitle = this.selectedGame.title;
            this.editDescription = this.selectedGame.description;
            this.editDeveloper = this.selectedGame.developer;
            this.editImageURL = this.selectedGame.image;
            this.editSales = this.selectedGame.sales;
            this.editPrice = this.selectedGame.price;
            this.editPublisher = this.selectedGame.publisher; 
            this.editStock = this.selectedGame.stock; 
            this.editReleaseDate = this.selectedGame.date;
            this.editDiscount = this.selectedGame.discount;
            this.editRating = this.selectedGame.rating;
            this.editSelectedGenres = this.selectedGame.genre;
            this.editSelectedPlatforms = this.selectedGame.platforms;
        },

        editGame(id) {
            console.log(id);
            const sales = parseFloat(this.editSales);
            const price = parseFloat(this.editPrice);
            const gameEdit = {
                title: this.editTitle,
                description: this.editDescription,
                developer: this.editDeveloper,
                publisher: this.editPublisher,
                imageURL: this.editImageURL,
                sales: sales,
                stock: this.editStock,
                price: price,
                releaseDate: this.editReleaseDate,
                discount: this.editDiscount,
                rating: this.editRating,
                genres: this.editSelectedGenres,
                platforms: this.editSelectedPlatforms
            };
        
            Swal.fire({
                title: "Do you want to save the changes?",
                showDenyButton: true,
                showCancelButton: true,
                confirmButtonText: "Save",
                denyButtonText: `Don't save`
            }).then((result) => {
                if (result.isConfirmed) {
                    axios.patch(`/api/games/${id}`, gameEdit)
                        .then(() => {
                            this.loadData();
                            this.clearFields();
                            Swal.fire("Saved!", "", "success");
                        })
                        .catch((error) => {
                            console.error(error);
                            Swal.fire({
                                icon: "error",
                                title: "Oops...",
                                text: "Something went wrong!",
                            });
                        });
                } else if (result.isDenied) {
                    Swal.fire("Changes are not saved", "", "info");
                }
            });
        },

        clearFields() {
            this.title = "";
            this.description = "";
            this.developer = "";
            this.imageURL = "";
            this.stock = "";
            this.sales = 0;
            this.price = 0;
            this.publisher = "";
            this.releaseDate = "";
            this.discount = 0;
            this.rating = "";
            this.selectedGenres = [];
            this.selectedPlatforms = [];
            this.editTitle = "";
            this.editDescription = "";
            this.editImageURL = "";
            this.editPrice = "";
            this.editSelectedGenres = [];
            this.editSelectedPlatforms = [];
            this.editReleaseDate = "";
            this.editSales = "";
            this.editRating = "";
            this.editDeveloper = "";
            this.editDiscount = "";
            this.editStock = "";
            this.editPublisher = "";
        }
    }
});

app.mount("#app");