package net.michalp.typesystemgoodies

case class UnsafeOrderLine(product: String, quantity: Int)
object UnsafeOrderLine {
 def safeApply(product: String, quantity: Int): UnsafeOrderLine = {
   if(product.isBlank())
     throw new RuntimeException("Product is empty")
   else if(quantity <= 0)
     throw new RuntimeException("Quantity lower than 1")
   else
     UnsafeOrderLine(product, quantity)
 }


  // Works fine!
  UnsafeOrderLine.safeApply("123", 10)
  // Throws runtime exception 👇
  UnsafeOrderLine.safeApply("", 10)
}