<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>test tomcat</title>

    <style>
      .form {
        display: flex;
        flex-direction: column;
      }
    </style>
  </head>
  <body>
    <h1>create product</h1>
    <form
      action="createProduct"
      method="POST"
      class="form"
      accept-charset="UTF-8"
    >
      <label for="name">
        Name:
        <input id="name" type="text" name="name" />
      </label>

      <label for="prcie">
        Price:
        <input id="price" type="number" name="price" />
      </label>

      <label for="description">
        description
        <input id="description" type="text" name="description" />
      </label>

      <div>
        <button type="submit">submit</button>
      </div>
    </form>
  </body>
</html>
