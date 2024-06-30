import React from "react";
import CustomerSearch from "./components/CustomerSearch";
import "bootstrap/dist/css/bootstrap.css";

function App() {
  return (
    <div className="App">
      <header className="App-header">
        <h3 className="text-center"> Customer Search</h3>
      </header>
      <CustomerSearch />
    </div>
  );
}

export default App;
