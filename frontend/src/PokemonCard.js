import React, { Component } from 'react'

export default function PokemonCard(props) {

        const pokemonStyle = {backgroundColor: `${props.pmon.types[0].color}`};

        return (
            <div class="col-8 col-md-6 col-lg-4" >
                <div class="d-flex flex-column border border-3 border-secondary rounded-3 justify-content-center" style={pokemonStyle}>
                    <div class="d-flex flex-row p-3 justify-content-between">
                        <h5 key="uniqueId1"  class="">{props.pmon.name}</h5>
                        <h5 key="uniqueId2" class="">#{props.pmon.id}</h5>
                    </div>
                    <img key="uniqueId3" src={`${props.pmon.imgSrc}`} class="justify-center " alt="..."/>
                    <div class="d-flex flex-row p-3 pt-0 ">
                        {props.pmon.types.map(t => {
                            return <div class="align-self-start fw-bold me-5 fst-italic" style={pokemonStyle}>{t.name} </div>
                        })}
                    </div>
                </div>
            </div>
        );
}