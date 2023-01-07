import React, { Component } from 'react';
import PokemonCard from './PokemonCard';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

class ShowPokemon extends Component {

    constructor(props) {
        super(props);
        this.state = { pokemon: [], filteredPokemon: [] };
    }

    componentDidMount() {
        fetch('/pokemon')
            .then(response => response.json())
            .then(data => this.setState({ pokemon: data , filteredPokemon: data}));
    }

    render() {
        const { pokemon, filteredPokemon, isLoading } = this.state;


        if (isLoading) {
            return <p>Loading...</p>;
        }

        const handleFilter = (event: React.ChangeEvent<HTMLInputElement>) => {
            console.log(event.target.value);
            let _filteredPokemon = pokemon.filter(p =>
                p.name
                    .toLocaleLowerCase()
                    .includes(event.target.value.toLocaleLowerCase())
            );
            this.setState({ filteredPokemon: _filteredPokemon});
        }

        const pokemonCards = filteredPokemon.map(p => {
                    return <PokemonCard pmon={p}/>
                });

        return(
            <div class = "py-5  bg-dark" >
                <Container fluid >
                    <h1 class="pb-3 d-flex justify-content-center text-white mb-5"> Pokedex < /h1>

                    <div class="d-flex justify-content-center mb-4">
                        <div class="w-25 ">
                            <input type="search" id="form1" class="form-control" onChange={handleFilter} />
                            <label class="form-label" htmlFor="form1">Search</label>
                        </div>
                    </div>

                    <div class="container justify-content-center" >
                        <div class="row gy-5 gx-5" >
                            {pokemonCards}
                        </div>
                    </div>
                </Container>
            </div>
            );
    }
}
export default ShowPokemon;