const { createApp } = Vue;

let app = createApp({
    data() {
        return {
            navMenu: false,
            games:[],

        };
    },

    methods: {
        loadData(){
            axios.get("/api/games")
            .then(response => {
                this.games = response.data
                console.log("Games", this.games)
            })
            .catch(error => {
                if (error.response) {
                    Swal.fire({
                        background: "linear-gradient(to right, #191970, #00BFFF) no-repeat 0 0 / cover",
                        color: "white",
                        icon: 'error',
                        title: 'Dear customer, we must inform you:',
                        text: `${JSON.stringify(error.response.data, null, 2)}`,
                        footer:  `Error de respuesta: ${error.response.status}`
                    });
                }
            });
            
        },

        // filtrarjuegos(){
        //     axios.post("/api/games/filter", this.filtro)
        //     .then(response => {
        //         this.games = response.data
        //         console.log("Games", this.games)

        //     })
        // },
        showMenu() {
            if (this.navMenu == false) {
                this.navMenu = true
            }

            else {
                this.navMenu = false
            }

        }
    }


}).mount("#app");