/*
 * Copyright 2022 mchalp.net
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.michalp.typesystemgoodies

case class UnsafeOrderLine(product: String, quantity: Int)
object UnsafeOrderLine {
  def safeApply(product: String, quantity: Int): UnsafeOrderLine = {
    if (product.isEmpty())
      throw new RuntimeException("Product is empty")
    else if (quantity <= 0)
      throw new RuntimeException("Quantity lower than 1")
    else
      UnsafeOrderLine(product, quantity)
  }

  // Works fine!
  UnsafeOrderLine.safeApply("123", 10)
  // Throws runtime exception 👇
  UnsafeOrderLine.safeApply("", 10)
}
