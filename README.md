



#  GET Endpoints in main MyShopController

## 1. Get all products
```http
  GET /api/action/products
```
* ***Description:*** 

This endpoint makes an HTTP GET request to retrieve a list of products. The response will be in JSON format and will contain an array of products with their details such as id, name, description, price, stock quantity, category, and images. Additionally, the response includes information about the pagination such as page number, page size, sorting, and total elements.

* **Query Parameters:**

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page`    | `number` | The page number to return. |
| `size`    | `number` | The page size to return.   |
| `sort`    | `string` | The sorting criteria.      |


## 2. Get all categories
```http
  GET /api/action/categories
```
* ***Description:***

This endpoint makes an HTTP GET request to retrieve a list of categories. The response will be in JSON format and will contain an array of categories with their details such as id, name, and description. Additionally, the response includes information about the pagination such as page number, page size, sorting, and total elements.

* **Query Parameters:**

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `page`    | `number` | The page number to return. |
| `size`    | `number` | The page size to return.   |
| `sort`    | `string` | The sorting criteria.      |

## 3. Get all products containing a specific keyword/name
```http
  GET /api/action/productsContainingName
```
* ***Description:***

This API endpoint makes an HTTP GET request to retrieve a list of products containing a specific name. The request should include the "name" parameter to specify the name to search for, and the "page" and "size" parameters for pagination.

* **Query Parameters:**

| Parameter | Type     | Description                |
|:----------| :------- | :------------------------- |
| `name`    | `string` | The name to search for.    |
| `page`    | `number` | The page number to return. |
| `size`    | `number` | The page size to return.   |


## 4. Get all products by category id
```http
  GET /api/action/productsByCategoryId
```
* ***Description:***

This API endpoint makes an HTTP GET request to retrieve a list of products by category id. The request should include the "categoryId" parameter to specify the category id to search for, and the "page" and "size" parameters for pagination.

* **Query Parameters:**

| Parameter   | Type     | Description                |
|:------------| :------- | :------------------------- |
| `categoryId`| `number` | The category id to search for. |
| `page`      | `number` | The page number to return. |
| `size`      | `number` | The page size to return.   |
| `sort`      | `string` | The sorting criteria.      |


## 5. Get Product by Id
```http
  GET /api/action/product/{productId}
```

* ***Description***

This endpoint makes an HTTP GET request to retrieve details of a specific product with ID. The response will be in JSON format and will include the product's ID, name, description, price, stock quantity, category details, and images associated with the product.


* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "id": "number",
  "productName": "String",
  "productDescription": "String",
  "productPrice": "number",
  "productStockQuantity": "number",
  "category": {
    "id": "number",
    "categoryName": "String",
    "products": []
  },
  "images": [
    {
      "id": "number",
      "name": "String",
      "type": "String",
      "picByte": "String"
    }
  ]
}
```

* **Error messages**
    * **Code:404
      "message": "No value present"

    

# POST Endpoints in main MyShopController

## 1. Save a new product
```http
  POST /api/action/saveProduct
```

* ***Description:***

This API endpoint makes an HTTP POST request to save a new product. The request should include the product details such as name, description, price, stock quantity, category (it's id), and images with name,type and picByte code.
if there's no such a category with a given id it creates new category with given category name 

* **Request Body:**

```json
{
  "productName": "string",
  "productDescription": "string",
  "productPrice": "number",
  "productStockQuantity": "number",
  "category": {
    "id": "number"
  },
  "images": [
    {
      "name": "string",
      "type": "string",
      "picByte": "string"
    }
  ]
}
```

* **Success Response**
  * **Code:200**
  <br>
  response body:
```json
{
  "productName": "string",
  "productDescription": "string",
  "productPrice": "number",
  "productStockQuantity": "number",
  "category": {
    "id": "number",
    "categoryName": "string"
  },
  "images": [
    {
      "name": "string",
      "type": "string",
      "picByte": "string"
    }
  ]
}
```
* **Error messages**
    * **Code:404
      "message": "Category id not found in database. Error message: No value present",
  #### OR ####
    * **Code:400
      "message": "Category name already exists.

## 2. Save a new Category

```http
  POST /api/action/saveCategory
```

* ***Description:***

This API endpoint makes an HTTP POST request to save a new category. The request should include the category name.

* **Request Body:**

```json
{
  "categoryName": "string"
}
```

* **Success Response**
  * **Code:200**
  <br>
  response body:
```json
{
  "id": "number",
  "categoryName": "string",
  "products": []
}
```

* **Error messages**
    * **Code:400
      "message": "Category name already exists.


# DELETE Endpoints in main MyShopController

## 1. Delete product by Id

```http
  DELETE /api/action/deleteProduct/{productId}
```

* ***Description:***

This API endpoint makes an HTTP POST request to delete a product by its id.

* **Success Response**
  * **Code:200**
  <br>
  response body:
```json
{
  "id": "number",
  "productName": "string",
  "productDescription": "string",
  "productPrice": "number",
  "productStockQuantity":"number",
  "category": {
    "id": "number",
    "categoryName": "string"
  },
  "images": [
    {
      "id": "number",
      "name": "string",
      "type": "string",
         "picByte": "string"
    }
  ]
}
```

* **Error messages**
    * **Code:404
      "message": "Product id not found in database. Error message: No value present"

## 2. Delete category by Id

```http
  DELETE /api/action/deleteCategory/{categoryId}
```

* ***Description:***

This API endpoint makes an HTTP DELETE request to delete a category by its id and all its products.

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "id": "number",
  "categoryName": "string",
  "products": []
}
```
* **Error messages**
    * **Code:404
      "message": "No such element found in database"

## 3. Delete image by Id

```http
  DELETE /api/action/deleteImage/{imageId}
```

* ***Description***

This API endpoint makes an HTTP DELETE request to delete an image with a given id from database.

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "id": "number",
  "name": "string",
  "type": "string",
  "picByte": "string"
}
```
* **Error messages**
    * **Code:404
      "message": "No such element found in database"

# PATCH Endpoints in main MyShopController

## 1. Update Category by Id

```http
  PATCH /api/action/updateCategory/{categoryId}
```

* ***Description***

This HTTP PATCH request is used to update a specific category with the provided ID. The request should include a payload with the category details to be updated.

* **Request Body**
```json
{
  "categoryName": "String"
}

```

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "id": 0,
  "categoryName": "String",
  "products": [
    {
      "id": 0,
      "productName": "",
      "productDescription": "",
      "productPrice": 0,
      "productStockQuantity": 0,
      "images": [
        {
          "id": 0,
          "name": "",
          "type": "",
          "picByte": ""
        }
      ]
    }
  ]
}

```

* **Error messages**
    * **Code:404
      "message": "No value present"

## 2. Update Product by Id

```http
  PATCH /api/action/product/{productId}
```

* ***Description***

This HTTP PATCH request is used to update a specific product with the given ID. The request should include the updated details of the product in the raw request body.


* **Request Body**
```json
{
  "id": "number",
  "productName": "String",
  "productDescription": "String",
  "productPrice": "number",
  "productStockQuantity": "number",
  "category": {
    "id": "number",
    "categoryName": "String",
    "products": []
  },
  "images": [
    {
      "id": "number",
      "name": "String",
      "type": "String",
      "picByte": "String"
    }
  ]
}

```

* **Success Response**
    * **Code:200**
      <br>
      response body is just updated request Body

* **Error messages**
    * **Code:404
      "message": "No value present"


____________________________________________________________________


# CheckoutController managing orders and payments

```http
  POST  /api/checkout/purchase
```

## 1.Place a purchase in database

* ***Description***

This endpoint allows you to make a purchase by providing customer details, address, and order information.

* **Request Body**
```json
{
  "customer":{
    "firstName":"String",
    "lastName":"String",
    "email":"String"
  },

  "address":{
    "street":"String",
    "city":"String",
    "state":"String",
    "country":"String",
    "zipCode":"String"
  },
  "order":{
    "totalPrice":36.98,
    "totalQuantity":2
  },
  "orderItems":[
    {
      "quantity":1,
      "unitPrice":18.99,
      "product":
      {
        "id":1
      }
    },
    {
      "quantity":1,
      "unitPrice":17.99,
      "productId":51,
      "product": {
        "id": 1
      }
    }
  ]
}
```

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "orderTrackingNumber": "5246899f-30f1-4763-8ad8-e3eb0c6ec9f2"
}
```

## 2. Creating payment Intent in order to manage Stripe

```http
  POST  /api/checkout/payment-intent
```

* ***Description***

Retrieving paymetn intent from Stripe API

* **Request Body**
```json
{
  "amount": "number",
  "currency": "string",
  "receiptEmail": "string"
}
```
* **Success Response**
    * **Code:200**
      <br>
      response body from Stripe API
___________________________________________________________

# AuthentucationController

## 1. Endpoint providing registering new user and getting JWT token

```http
  POST  /api/v1/auth/register
```

* **Description**

This HTTP POST request is used to register a new user for authentication. The request should include the user's first name, last name, email, and password in the raw request body. Upon successful execution, the response will have a status code of 200 and return a JSON object containing a token for authentication.

* **Request Body**
```json
{
  "firstName": "",
  "lastName": "",
  "email": "",
  "password": ""
}
```
* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "token": "JwtToken"
}
```

## 2. Endpoint providing registering new user and getting JWT token

```http
  POST  /api/v1/auth/authenticate
```

* **Description**

This endpoint is used to authenticate a user and obtain a  JWT token for accessing protected resources.

* **Request Body**
```json
{
  "email": "",
  "password": ""
}
```
* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "token": "JwtToken"
}
```

## 3. Endpoint for retrieving users role USER or ADMIN from JWT token 

```http
  GET  /api/v1/auth/roles
```

* **Description**

This endpoint makes an HTTP GET request to retrieve the roles from the server. The response will be in JSON format with a "rolesString" field indicating the roles available.

* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "rolesString": "USER,ADMIN"
}
```
________________________
# OrdersController

## 1. retrieving all orders

* **Description**

  Endpoint accessible for ADMIN users providing insight into all placed orders with details

```http
  GET  /api/orders/allOrders
```

* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "orders": [
    {
      "id": 1,
      "orderTrackingNumber": "",
      "totalQuantity": 2,
      "totalPrice": 800.00,
      "status": "",
      "dateCreated": "",
      "lastUpdated": "",
      "customer": {
        "firstName": "",
        "lastName": "",
        "email": "",
        "phoneNumber": "",
        "street": "",
        "city": "",
        "country": "",
        "zipCode": "",
        "state": ""
      }
    }
  ]
}
```

## 2. retrieving user orders

* **Description**

  Endpoint accessible for USERs showing only orders related to their email

```http
  GET  /api/orders/customerOrders
```

* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "orders": [
    {
      "id": 1,
      "orderTrackingNumber": "",
      "totalQuantity": 2,
      "totalPrice": 800.00,
      "status": "",
      "dateCreated": "",
      "lastUpdated": "",
      "customer": {
        "firstName": "",
        "lastName": "",
        "email": "",
        "phoneNumber": "",
        "street": "",
        "city": "",
        "country": "",
        "zipCode": "",
        "state": ""
      }
    }
  ]
}
```








































  




