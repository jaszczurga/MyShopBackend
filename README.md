
# MyShop API
 This project is a simple e-commerce API where users can view products, add them to cart and place orders with payments powered by Stripe. Admin can add, update and delete products. Admin can also view orders placed by users.

* [Frontend implementation of e-commerce Website in Angular](https://github.com/jaszczurga/MyShopAngularFront)

## Table of Contents
1. [Technologies](#technologies)
2. [Setup](#setup)
3. [Key Features](#key-features)
4. [Endpoints](#endpoints)


## Technologies
* Java 21
* Spring Boot
* Spring Security
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* Lombok
* JWT
* Stripe
* WebSockets
* STOMP
* SOCKJS


## Setup

* **Prerequisites:** 
    * Java 21
    * Maven
    * MySQL (use port 3307 and run docker compose file from main directory to create database)
____________________________

1. Clone the repository

```bash
git clone
```

2. docker compose file run in order to create database
```bash
docker-compose up
```

3. Install the dependencies

```bash
mvn clean install
```

4. Run the application

```bash
mvn spring-boot:run
```

## Key Features:
* The platform boasts an intuitive and visually appealing user interface, ensuring a smooth navigation experience for customers.
* Users can explore a wide range of products conveniently categorized for easy browsing. Each product is presented with description and images.
* Customers can add desired items to their shopping cart with a single click. The cart dynamically updates to display the added products, allowing users to review their selections before proceeding to checkout.
* Users can place orders with payments powered by Stripe.
* Admin can add, update and delete products. Admin can also view orders placed by users.
* The platform is equipped with a live chat feature, allowing users to communicate with customer service representatives in real-time.
* The application is secured with Spring Security and JWT, ensuring that only authorized users can access protected resources.
* The platform is designed to handle exceptions gracefully, providing users with informative error messages in the event of an issue.

## Endpoints


* [Managing products and categories](#myshopcontroller)
    * [MyShopController GET](#GET-Endpoints-in-main-MyShopController)
    * [MyShopController POST](#POST-Endpoints-in-main-MyShopController)
    * [MyShopController DELETE](#DELETE-Endpoints-in-main-MyShopController)
    * [MyShopController PATCH](#PATCH-Endpoints-in-main-MyShopController)
* [Placing orders and payments](#checkoutcontroller-managing-orders-and-payments)
* [AuthentucationController](#authentucationcontroller)
* [Managing placed orders](#orderscontroller)
* [Live Chat](#live-chat)



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

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "_embedded": {
    "products": [
      {
        "id": 1,
        "productName": "Grand Cherokke",
        "productDescription": "Reliable and dynamic car with an elegant design. Perfectly balanced between comfort and performance. Richly equipped with the latest technologies ensuring safety and comfort during the journey. An ideal companion both in the urban jungle and on open roads.",
        "productPrice": 100000.0,
        "productStockQuantity": 23,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/products/1"
          },
          "product": {
            "href": "http://localhost:8080/api/products/1"
          },
          "images": {
            "href": "http://localhost:8080/api/products/1/images"
          },
          "category": {
            "href": "http://localhost:8080/api/products/1/category"
          }
        }
      }
    ]
  },
  "_links": {
    "first": {
      "href": "http://localhost:8080/api/products?page=0&size=20"
    },
    "self": {
      "href": "http://localhost:8080/api/products?page=0&size=20"
    },
    "next": {
      "href": "http://localhost:8080/api/products?page=1&size=20"
    },
    "last": {
      "href": "http://localhost:8080/api/products?page=1&size=20"
    },
    "profile": {
      "href": "http://localhost:8080/api/profile/products"
    },
    "search": {
      "href": "http://localhost:8080/api/products/search"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 21,
    "totalPages": 2,
    "number": 0
  }
}
```


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

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "_embedded": {
    "productCategories": [
      {
        "id": 6,
        "categoryName": "city car",
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/productCategories/6"
          },
          "productCategory": {
            "href": "http://localhost:8080/api/productCategories/6"
          },
          "products": {
            "href": "http://localhost:8080/api/productCategories/6/products"
          }
        }
      }
    ]
  }
}
```

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

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "_embedded": {
    "products": [
      {
        "id": 1,
        "productName": "Grand Cherokke",
        "productDescription": "Reliable and dynamic car with an elegant design. Perfectly balanced between comfort and performance. Richly equipped with the latest technologies ensuring safety and comfort during the journey. An ideal companion both in the urban jungle and on open roads.",
        "productPrice": 100000.0,
        "productStockQuantity": 23,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/products/1"
          },
          "product": {
            "href": "http://localhost:8080/api/products/1"
          },
          "images": {
            "href": "http://localhost:8080/api/products/1/images"
          },
          "category": {
            "href": "http://localhost:8080/api/products/1/category"
          }
        }
      }
    ]
  },
  "_links": {
    "first": {
      "href": "http://localhost:8080/api/products/search/productsContainingName?name=Grand+Cherokke&page=0&size=20"
    },
    "self": {
      "href": "http://localhost:8080/api/products/search/productsContainingName?name=Grand+Cherokke&page=0&size=20"
    },
    "next": {
      "href": "http://localhost:8080/api/products/search/productsContainingName?name=Grand+Cherokke&page=1&size=20"
    },
    "last": {
      "href": "http://localhost:8080/api/products/search/productsContainingName?name=Grand+Cherokke&page=1&size=20"
    },
    "profile": {
      "href": "http://localhost:8080/api/profile/productsContainingName"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 21,
    "totalPages": 2,
    "number": 0
  }
}
```


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

* **Success Response**
    * **Code:200**
      <br>
      response body:
```json
{
  "_embedded": {
    "products": [
      {
        "id": 1,
        "productName": "Grand Cherokke",
        "productDescription": "Reliable and dynamic car with an elegant design. Perfectly balanced between comfort and performance. Richly equipped with the latest technologies ensuring safety and comfort during the journey. An ideal companion both in the urban jungle and on open roads.",
        "productPrice": 100000.0,
        "productStockQuantity": 23,
        "_links": {
          "self": {
            "href": "http://localhost:8080/api/products/1"
          },
          "product": {
            "href": "http://localhost:8080/api/products/1"
          },
          "images": {
            "href": "http://localhost:8080/api/products/1/images"
          },
          "category": {
            "href": "http://localhost:8080/api/products/1/category"
          }
        }
      }
    ]
  },
  "_links": {
    "first": {
      "href": "http://localhost:8080/api/products/search/productsByCategoryId?categoryId=1&page=0&size=20"
    },
    "self": {
      "href": "http://localhost:8080/api/products/search/productsByCategoryId?categoryId=1&page=0&size=20"
    },
    "next": {
      "href": "http://localhost:8080/api/products/search/productsByCategoryId?categoryId=1&page=1&size=20"
    },
    "last": {
      "href": "http://localhost:8080/api/products/search/productsByCategoryId?categoryId=1&page=1&size=20"
    },
    "profile": {
      "href": "http://localhost:8080/api/profile/productsByCategoryId"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 21,
    "totalPages": 2,
    "number": 0
  }
}
```


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
      "messageDto": "No value present"

    

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
      "messageDto": "Category id not found in database. Error messageDto: No value present",
  #### OR ####
    * **Code:400
      "messageDto": "Category name already exists.

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
      "messageDto": "Category name already exists.


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
      "messageDto": "Product id not found in database. Error messageDto: No value present"

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
      "messageDto": "No such element found in database"

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
      "messageDto": "No such element found in database"

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
      "messageDto": "No value present"

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
      "messageDto": "No value present"


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

# Live-Chat

## table of contents for live chat relations in sending messages 
for example <br>
manager => means that manager is sending message to customer and <br>
manager <= customer means that manager is receiving message from customer

1. [manager => customer](#ws-endpoint-for-sending-messages-to-the-customer)
2. [customer => manager](#ws-endpoint-for-sending-messages-to-the-manager)
3. [manager <= customer](#ws-endpoint-for-receiving-messages-from-the-customer)
4. [customer <= manager](#ws-endpoint-for-sending-messages-to-the-customer)


____________________

* **Description**

  Live chat functionality is implemented using WebSockets, STOMP, and SOCKJS. The chat feature allows users to communicate with customer service representatives in real-time.

* **How to use:**

    Live-Chat is based on <br>
    * **WSService** responsible for managing WebSocket connection
    * **ChatService** responsible for managing chat messages and conversations

    Primary Object for handling requests is **MessageDto** for transfer and **Message Entity** for database storage

* **MessageDto**

```json
{
  "receiverId": "3",
  "content": "Dzień dobry! Czy mogę w czymś pomóc?",
  "senderId": "1",
  "timestamp": "2024-03-09T08:26:52.000+00:00"
}
```

In order to create WebSocket Connection you need to connect to 
```websocket
  WS /socket
```
_________________________________
### WS Endpoint for sending messages to the customer

* **Description**

  This endpoint is used to send messages to the customer and is managed by MessageController and storing in WSService. Manager panel is responsible for sending messages to the customer. After sending messages are stored in db by ChatService

```websocket
   /ws/send-message-to-customer
```
* **Request Body**
```json
{
  "receiverId": "3",
  "content": "Dzień dobry! Czy mogę w czymś pomóc?",
  "senderId": "1"
}
```
______________________________
### WS Endpoint for sending messages to the manager

* **Description**

  This endpoint is used to send messages to the manager and is managed by MessageController and storing in WSService . Customer is responsible for sending messages to the manager. After sending messages are stored in db by ChatService

```websocket
   /ws/send-message-to-shop-manager
```

* **Request Body**
```json
{
  "receiverId": "1",
  "content": "Dzień dobry! Czy mogę w czymś pomóc?",
  "senderId": "3"
}
```
___________________
### WS Endpoint for receiving messages from the customer

* **Description**

  This endpoint is used to receive messages from the customer and is managed by WSService. Manager panel is responsible for receiving messages from the customer.

```websocket
   /topic/messages-from-customers
```

* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "receiverId": "3",
  "content": "Dzień dobry! Czy mogę w czymś pomóc?",
  "senderId": "1",
  "timestamp": "2024-03-09T08:26:52.000+00:00"
}
```
_____________________________
### WS Endpoint for receiving messages from the manager

* **Description**

  This endpoint is used to receive messages from the manager and is managed by WSService. Customer is responsible for receiving messages from the manager.

```websocket
   /topic/messages-from-manager
```

* **Success Response**
    * **Code:200**
      <br>
      response body
```json
{
  "receiverId": "1",
  "content": "Dzień dobry! Czy mogę w czymś pomóc?",
  "senderId": "3",
  "timestamp": "2024-03-09T08:26:52.000+00:00"
}
```







































  




