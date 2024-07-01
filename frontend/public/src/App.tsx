import React from "react";
import CustomerSearch from "./components/CustomerSearch";
import ReactDOM from "react-dom";
import { Route, Routes } from "react-router-dom";

const App = () => {
  return (
    <div>
      <Routes>
        <Route path="/" element={<CustomerSearch />} />
        <Route path="/demo/*" element={<CustomerSearch />} />
      </Routes>
    </div>
  );
};
export default App;
