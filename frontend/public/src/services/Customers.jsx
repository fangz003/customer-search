const BASE_URL = "http://localhost:8080/demo/customers/search/";

export const searchAllCustomers = async () => {
  const path = "listAll";
  try {
    const response = await fetch(`${BASE_URL}${path}`);
    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const searchCustomersByFirstName = async (params) => {
  const path = "searchByFirstName";
  try {
    const response = await fetch(`${BASE_URL}${path}?${params.toString()}`);

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const searchCustomersByLastName = async (params) => {
  const path = "searchByLastName";
  try {
    const response = await fetch(`${BASE_URL}${path}?${params.toString()}`);

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};

export const searchCustomersByCompanyId = async (params) => {
  const path = "searchByCompanyId";
  try {
    const response = await fetch(`${BASE_URL}${path}?${params.toString()}`);

    if (!response.ok) {
      throw new Error("Network response was not ok");
    }
    return await response.json();
  } catch (error) {
    throw error;
  }
};
