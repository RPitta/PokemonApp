import logo from './logo.svg';
import './App.css';
import React from "react"

class App extends React.Component {
  state = {
    pokemon: []
  };

  async componentDidMount() {
    const response = await fetch('/pokemon');
    const body = await response.json();
    this.setState({pokemon: body})
  }

  render() {
    const {pokemon} = this.state;
    return (
        <div className="App">
          <header className="App-header">
            <img src={logo} className="App-logo" alt="logo" />
            <div className="App-intro">
              <h2>Clients</h2>
              {pokemon.map(p =>
                <div key={p.id}>
                    {p.name} ({p.imgSrc})
                     </div>
                )}
            </div>
          </header>
        </div>
    );
  }
}
export default App;
