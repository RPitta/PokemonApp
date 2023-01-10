import React, { Component } from 'react';
import './App.css';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import ShowPokemon from './ShowPokemon';

class App extends Component {
  render() {
    return (
      <Router>
        <Switch>
          <Route path='/' exact={true} component={ShowPokemon} />
        </Switch>
      </Router>
    )
  }
}

export default App;