const deleteProduct = id => {
  if (confirm('是否要刪除?')) {
    location.href = `deleteProduct?id=${id}`;
  }
};
