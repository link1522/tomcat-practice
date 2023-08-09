const deleteProduct = id => {
  if (confirm('Are you sure to delete the product?')) {
    location.href = `product?operate=delete&id=${id}`;
  }
};
