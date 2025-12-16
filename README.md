# Marketing Application

A Spring Boot REST API for managing marketing campaigns, products, and sellers.
## API Endpoints

### Sellers
- `POST /sellers` - Create a new seller
- `GET /sellers` - List all sellers
- `GET /sellers/{id}` - Get seller details
- `POST /sellers/{id}/topup` - Top-up seller account

### Products
- `POST /products/sellers/{sellerId}/products` - Create a product
- `GET /products/sellers/{sellerId}/products` - List seller's products
- `GET /products/{id}` - Get product details

### Campaigns
- `POST /campaigns/products/{productId}/campaigns` - Create a campaign
- `GET /campaigns/products/{productId}/campaigns` - List product's campaigns
- `GET /campaigns/{id}` - Get campaign details
- `PUT /campaigns/{id}` - Update a campaign
- `DELETE /campaigns/{id}` - Delete a campaign

### Dictionary
- `GET /dictionary/towns` - List all towns
- `GET /dictionary/keywords` - Search keywords

