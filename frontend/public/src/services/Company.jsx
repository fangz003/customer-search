export const fetchCompanies = () => {
  return fetch("http://localhost:8080/demo/companies/listAll")
    .then((response) => {
      if (!response.ok) {
        throw new Error("Network response was not ok " + response.statusText);
      }
      return response.json();
    })
    .catch((error) => {
      console.error("Error fetching companies:", error);
      throw error; // rethrow so caller can handle it
    });
};
