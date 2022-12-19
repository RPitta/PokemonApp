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
            return <tr key={p.id}>
                <td style={{whiteSpace: 'nowrap'}}>{p.name}</td>
                <td>{p.imgSrc}</td>
                <td>

                </td>
            </tr>
        });

        return (
            <div class="p-5">
                <Container fluid >

                    <h3 class="pb-3 d-flex justify-content-center">Pokemon</h3>
                    <Table className="mt-4">
                        <thead>
                        <tr>
                            <th width="30%">Name</th>
                            <th width="30%">imgSrc</th>
                        </tr>
                        </thead>
                        <tbody>
                        {pokemonList}
                        </tbody>
                    </Table>
                </Container>
            </div>
        );
    }
}
export default ShowPokemon;