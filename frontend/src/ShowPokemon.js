import React, { Component } from 'react';
import PokemonCard from './PokemonCard';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';
import useScriptBody from './hooks/useScriptBody.js';
import useScriptHead from './hooks/useScriptHead.js';

class ShowPokemon extends Component {

    constructor(props) {
        super(props);
        this.state = { pokemon: [] };
    }

    componentDidMount() {
        fetch('/pokemon')
            .then(response => response.json())
            .then(data => this.setState({ pokemon: data }));
    }

    render() {
        const { pokemon, isLoading } = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const pokemonCards = pokemon.map(p => {
            return <PokemonCard pmon={p}/>
        });

        return(
            <div class = "py-5  bg-dark" >
                <Container fluid >
                    <h1 class="pb-3 d-flex justify-content-center text-white mb-5"> Pokedex < /h1>
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