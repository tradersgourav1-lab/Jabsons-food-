package com.example.model

enum class ProductCategory(val displayName: String, val hindiName: String, val icon: String) {
  ALL("All Items", "सभी स्नैक्स", "✨"),
  PEANUTS("Peanuts", "मूंगफली", "🥜"),
  NAMKEEN("Namkeen", "नमकीन", "🍘"),
  ROASTED_SNACKS("Roasted Snacks", "रोस्टेड स्नैक्स", "🥨"),
  GIFT_PACKS("Gift Packs", "गिफ्ट पैक", "🎁"),
  COMBOS("Combos", "कॉम्बो", "⭐"),
  SWEETS("Sweets & Gajak", "मिठाई व गजक", "🍪")
}

data class Product(
  val id: Int,
  val name: String,
  val hindiName: String,
  val category: ProductCategory,
  val price: Int,
  val originalPrice: Int,
  val weight: String,
  val description: String,
  val hindiDescription: String,
  val emoji: String,
  val badge: String? = null,
  val spiceLevel: Int = 1, // 1: Mild, 2: Medium, 3: Spicy
  val isVeg: Boolean = true,
  val isRoasted: Boolean = true,
  val rating: Float = 4.8f,
  val reviewCount: Int = 142,
  val ingredients: String = "Peanuts, Edible Vegetable Oil, Iodized Salt, Spices & Condiments",
  val shelfLife: String = "6 Months from packaging",
  val storageAdvice: String = "Store in a cool, dry place inside an airtight container"
)
