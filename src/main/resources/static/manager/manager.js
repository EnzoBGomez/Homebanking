let URLAPI = `http://localhost:8080/rest/clients`

Vue.createApp({
    data() {
        return{
            data:{},
            clients: [],
            first_name: "",
            last_name: "",
            email: "",
            password: "",

        }

    },
    created(){
        axios.get(URLAPI)
        .then(response => {
            this.clients = response.data._embedded.clients
            this.data = response.data
        })
        
    },
    methods:{
        limpiar_data(){
            this.first_name = "";
            this.last_name = "";
            this.email = "";
            this.password = "";
        },
        async agregar_usuario(){
            await axios.post(URLAPI, {
                firstName: this.first_name,
                lastName: this.last_name,
                email: this.email,
                password: this.password,
            })
            .then(function (response) {
                console.log(response);
            })
            .catch(function (error) {
                console.log(error);
            });
            this.limpiar_data()
            await this.actualizar_tabla()
        },
        async actualizar_tabla(){
            await axios.get(URLAPI)
            .then(response => {
                this.clients = response.data._embedded.clients
                this.data = response.data
            })
        }

    }









}).mount('#app')