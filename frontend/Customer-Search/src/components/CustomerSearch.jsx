// src/components/CustomerSearch.jsx

import React, { useState, useEffect } from "react";
import { fetchCompanies } from "../services/Company";

const CustomerSearch = () => {
  const [customers, setCustomers] = useState([]);
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [companies, setCompanies] = useState([]);
  const [companyId, setCompanyId] = useState("");
  const [searchResults, setSearchResults] = useState([]);
  const [validationMessage, setValidationMessage] = useState("");
  const [sortField, setSortField] = useState("firstName");
  const [sortDirection, setsortDirection] = useState("asc"); // asc or desc

  useEffect(() => {
    fetchCompanies()
      .then((data) => setCompanies(data))
      .catch((error) => console.error("Error fetching companies:", error));
  }, []);

  const handleSearch = () => {
    // Reset validation message
    setValidationMessage("");

    // Perform validation
    if (!firstName && !lastName && !companyId) {
      setValidationMessage("Please provide a valid search criteria");
      return;
    }
    if (firstName) {
      const params = new URLSearchParams({
        firstName,
        sortField,
        sortDirection,
      });
      // Perform the search based on the input values
      searchCustomersByFirstName(params)
        .then((data) => setSearchResults(data))
        .catch((error) =>
          console.error("Error searching customers by first name:", error)
        );
    } else if (lastName) {
      const params = new URLSearchParams({
        lastName,
        sortField,
        sortDirection,
      });
      searchCustomersByLastName(params)
        .then((data) => setSearchResults(data))
        .catch((error) =>
          console.error("Error searching customers by last name:", error)
        );
    } else if (companyId) {
      const params = new URLSearchParams({
        companyId,
        sortField,
        sortDirection,
      });
      searchCustomersByCompanyId(params)
        .then((data) => setSearchResults(data))
        .catch((error) =>
          console.error("Error searching customers by customer id:", error)
        );
    }
  };

  const handleSort = (field) => {
    const newsortDirection =
      sortField === field && sortDirection === "asc" ? "desc" : "asc";
    setSortField(field);
    setsortDirection(newsortDirection);
    handleSearch(); // Re-fetch data with the new sorting
  };

  return (
    <div className="container text-center">
      <div>
        <label htmlFor="firstName">
          First Name:
          <input
            type="text"
            className="form-control mx-3"
            value={firstName}
            onChange={(e) => setFirstName(e.target.value)}
          />
        </label>

        <label htmlFor="lastName">
          Last Name:
          <input
            type="text"
            className="form-control mx-3"
            value={lastName}
            onChange={(e) => setLastName(e.target.value)}
          />
        </label>

        <label htmlFor="customerId">
          Company ID:
          <select
            className="form-control"
            value={companyId}
            onChange={(e) => setCompanyId(e.target.value)}
          >
            <option value="">Select Company ID</option>
            {companies.map((company) => (
              <option key={company.id} value={company.id}>
                {company.name}
              </option>
            ))}
          </select>
        </label>
      </div>
      <br></br>
      <button className="btn btn-primary" onClick={handleSearch}>
        Search
      </button>

      {validationMessage && <p style={{ color: "red" }}>{validationMessage}</p>}

      <div width="80%">
        {searchResults.length > 0 ? (
          <table className="table table-striped">
            <thead>
              <tr>
                <th>
                  First Name
                  <button
                    className="btn btn-link"
                    onClick={() => handleSort("firstName")}
                  >
                    Sort{" "}
                    {sortField === "firstName"
                      ? sortDirection === "asc"
                        ? "↑"
                        : "↓"
                      : ""}
                  </button>
                </th>
                <th>
                  Last Name
                  <button
                    className="btn btn-link"
                    onClick={() => handleSort("lastName")}
                  >
                    Sort{" "}
                    {sortField === "lastName"
                      ? sortDirection === "asc"
                        ? "↑"
                        : "↓"
                      : ""}
                  </button>
                </th>
                <th>
                  Company Name
                  <button
                    className="btn btn-link"
                    onClick={() => handleSort("companyId")}
                  >
                    Sort{" "}
                    {sortField === "companyId"
                      ? sortDirection === "asc"
                        ? "↑"
                        : "↓"
                      : ""}
                  </button>
                </th>
              </tr>
            </thead>
            <tbody>
              {searchResults.map((result) => (
                <tr key={result.id}>
                  <td>{result.firstName}</td>
                  <td>{result.lastName}</td>
                  <td>{result.company.name}</td>
                </tr>
              ))}
            </tbody>
          </table>
        ) : (
          <p>No results found</p>
        )}
      </div>
    </div>
  );
};

function searchCustomersByFirstName(params) {
  return fetch(
    `http://localhost:8080/demo/customers/search/searchByFirstName?${params.toString()}`
  )
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json(); // assuming the response is JSON
    })
    .catch((error) => {
      console.error("Error:", error);
      throw error; // rethrow so caller can handle it
    });
}

function searchCustomersByLastName(params) {
  return fetch(
    `http://localhost:8080/demo/customers/search/searchByLastName?${params.toString()}`
  )
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json(); // assuming the response is JSON
    })
    .catch((error) => {
      console.error("Error:", error);
      throw error; // rethrow so caller can handle it
    });
}

function searchCustomersByCompanyId(params) {
  return fetch(
    `http://localhost:8080/demo/customers/search/searchByCompanyId?${params.toString()}`
  )
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json(); // assuming the response is JSON
    })
    .catch((error) => {
      console.error("Error:", error);
      throw error; // rethrow so caller can handle it
    });
}

export default CustomerSearch;
