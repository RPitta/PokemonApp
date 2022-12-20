import React, { Component } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import { Link } from 'react-router-dom';

class ShowPokemon extends Component {

    constructor(props) {
        super(props);
        this.state = {pokemon: []};
    }

    componentDidMount() {
        fetch('/pokemon')
            .then(response => response.json())
            .then(data => this.setState({pokemon: data}));
    }

    render() {
        const {pokemon, isLoading} = this.state;

        if (isLoading) {
            return <p>Loading...</p>;
        }

        const pokemonList = pokemon.map(p => {
            const pokemonStyle = {
                        backgroundColor: `${p.types[0].color}`
                    };

            return <div class="col-8 col-md-6 col-lg-4" >
                        <div class="d-flex flex-column border border-3 border-secondary rounded-3 justify-content-center" style={pokemonStyle}>
                            <div class="d-flex flex-row p-3 justify-content-between">
                                <h5 class="">{p.name}</h5>
                                <h5 class="">#{p.id}</h5>
                            </div>
                            <img src={`${p.imgSrc}`} class="justify-center " alt="..."/>
                            <div class="d-flex flex-row p-3 pt-0 ">
                                {p.types.map(t => {
                                    return <div class="align-self-start fw-bold me-5 fst-italic" style={pokemonStyle}>{t.name} </div>
                                })}

                            </div>
                        </div>
                    </div>

        });

        return (
            <div class="py-5  bg-dark">
                <Container fluid >
                    <h1 class="pb-3 d-flex justify-content-center text-white mb-5">Pokedex</h1>
                    <div class="container justify-content-center">
                        <div class="row gy-5 gx-5">
                            {pokemonList}
                        </div>

                    </div>
                </Container>
            </div>
        );
    }
}
export default ShowPokemon;