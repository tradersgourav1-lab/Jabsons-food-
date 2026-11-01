package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.QrCode2
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BhiwaniData
import com.example.model.CartItem
import com.example.model.Order
import com.example.ui.theme.AmberRoasted
import com.example.ui.theme.CreamBackground
import com.example.ui.theme.SaffronDark
import com.example.ui.theme.SaffronPrimary
import com.example.ui.theme.VegGreen
import com.example.viewmodel.JabsonsViewModel

@Composable
fun CartCheckoutScreen(
  viewModel: JabsonsViewModel,
  onGoBackToShopping: () -> Unit,
  onOrderSuccess: (Order) -> Unit,
  modifier: Modifier = Modifier
) {
  val cartItems = viewModel.getCartItems()
  val isHindi by viewModel.isHindi.collectAsState()
  val customerName by viewModel.customerName.collectAsState()
  val phoneNumber by viewModel.phoneNumber.collectAsState()
  val deliveryAddress by viewModel.deliveryAddress.collectAsState()
  val selectedArea by viewModel.selectedArea.collectAsState()
  val selectedSlot by viewModel.selectedSlot.collectAsState()
  val paymentMethod by viewModel.paymentMethod.collectAsState()
  val appliedCoupon by viewModel.appliedCoupon.collectAsState()
  val couponMessage by viewModel.couponMessage.collectAsState()

  var couponInput by remember { mutableStateOf("") }
  var validationError by remember { mutableStateOf<String?>(null) }
  var areaDropdownExpanded by remember { mutableStateOf(false) }
  var slotDropdownExpanded by remember { mutableStateOf(false) }

  val subtotal = viewModel.getCartSubtotal()
  val deliveryFee = viewModel.getDeliveryFee(subtotal)
  val discount = viewModel.getDiscount(subtotal)
  val total = viewModel.getTotalAmount()

  if (cartItems.isEmpty()) {
    Box(
      modifier = modifier
        .fillMaxSize()
        .background(CreamBackground)
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
      ) {
        Surface(
          modifier = Modifier.size(90.dp),
          shape = CircleShape,
          color = Color(0xFFFFF0D6)
        ) {
          Box(contentAlignment = Alignment.Center) {
            Icon(
              imageVector = Icons.Default.ShoppingBag,
              contentDescription = null,
              tint = SaffronPrimary,
              modifier = Modifier.size(44.dp)
            )
          }
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
          text = if (isHindi) "आपकी कार्ट खाली है" else "Your Cart is Empty",
          fontSize = 20.sp,
          fontWeight = FontWeight.Bold,
          color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(6.dp))
        Text(
          text = if (isHindi) "स्वादिष्ट मूंगफली और नमकीन कार्ट में जोड़ें" else "Explore delicious roasted peanuts, namkeen and snack combos",
          fontSize = 13.sp,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
          onClick = onGoBackToShopping,
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
          modifier = Modifier.testTag("explore_snacks_button")
        ) {
          Text(text = if (isHindi) "स्नैक्स देखें" else "Explore Snacks")
        }
      }
    }
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(CreamBackground)
      .verticalScroll(rememberScrollState())
      .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 100.dp)
  ) {
    // Header
    Row(
      modifier = Modifier.fillMaxWidth(),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically
    ) {
      Text(
        text = if (isHindi) "आपकी कार्ट (${cartItems.size})" else "Your Cart (${cartItems.size} items)",
        fontSize = 18.sp,
        fontWeight = FontWeight.Black,
        color = MaterialTheme.colorScheme.onSurface
      )
      Text(
        text = if (isHindi) "सभी हटाएं" else "Clear All",
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color(0xFFC75607),
        modifier = Modifier
          .clickable { viewModel.clearCart() }
          .padding(4.dp)
      )
    }

    Spacer(modifier = Modifier.height(12.dp))

    // Items list
    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        cartItems.forEachIndexed { index, cartItem ->
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = cartItem.product.emoji, fontSize = 28.sp)
            Spacer(modifier = Modifier.width(10.dp))
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (isHindi) cartItem.product.hindiName else cartItem.product.name,
                fontSize = 13.5.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = "${cartItem.product.weight} • ₹${cartItem.product.price} each",
                fontSize = 11.5.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            }

            // Stepper
            Row(
              modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFFFF2DF))
                .border(1.dp, SaffronPrimary.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                .padding(horizontal = 2.dp, vertical = 2.dp),
              verticalAlignment = Alignment.CenterVertically
            ) {
              IconButton(
                onClick = { viewModel.decreaseFromCart(cartItem.product.id) },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(imageVector = Icons.Default.Remove, contentDescription = "Decrease", tint = SaffronDark, modifier = Modifier.size(14.dp))
              }
              Text(
                text = "${cartItem.quantity}",
                fontWeight = FontWeight.Bold,
                fontSize = 13.sp,
                color = SaffronDark,
                modifier = Modifier.padding(horizontal = 6.dp)
              )
              IconButton(
                onClick = { viewModel.addToCart(cartItem.product.id) },
                modifier = Modifier.size(24.dp)
              ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Increase", tint = SaffronDark, modifier = Modifier.size(14.dp))
              }
            }

            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "₹${cartItem.totalPrice}",
              fontWeight = FontWeight.Bold,
              fontSize = 14.sp,
              color = SaffronDark
            )
          }

          if (index < cartItems.size - 1) {
            Divider(
              modifier = Modifier.padding(vertical = 10.dp),
              color = Color(0xFFF1EAE0)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Coupon Code Section
    Text(
      text = if (isHindi) "कूपन व डिस्काउंट" else "Coupons & Offers",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        if (appliedCoupon != null) {
          Row(
            modifier = Modifier
              .fillMaxWidth()
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFE8F5E9))
              .padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.CheckCircle, contentDescription = null, tint = VegGreen, modifier = Modifier.size(18.dp))
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = "Applied '$appliedCoupon' (-₹$discount)",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = VegGreen
              )
            }
            Text(
              text = "Remove",
              fontSize = 12.sp,
              fontWeight = FontWeight.Bold,
              color = Color.Red,
              modifier = Modifier.clickable { viewModel.removeCoupon() }
            )
          }
        } else {
          Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
          ) {
            OutlinedTextField(
              value = couponInput,
              onValueChange = { couponInput = it.uppercase() },
              placeholder = { Text("Enter coupon code", fontSize = 13.sp) },
              modifier = Modifier
                .weight(1f)
                .testTag("coupon_input"),
              singleLine = true,
              shape = RoundedCornerShape(10.dp),
              colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = SaffronPrimary,
                unfocusedBorderColor = MaterialTheme.colorScheme.outline
              )
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
              onClick = {
                if (couponInput.isNotBlank()) {
                  viewModel.applyCoupon(couponInput)
                  couponInput = ""
                }
              },
              shape = RoundedCornerShape(10.dp),
              colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
              modifier = Modifier.testTag("apply_coupon_button")
            ) {
              Text("Apply")
            }
          }
        }

        if (couponMessage != null) {
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = couponMessage ?: "",
            fontSize = 11.5.sp,
            color = if (appliedCoupon != null) VegGreen else Color(0xFFC75607)
          )
        }

        // Suggestions
        Spacer(modifier = Modifier.height(10.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFFF4E5),
            modifier = Modifier.clickable { viewModel.applyCoupon("BHIWANI50") }
          ) {
            Text(
              text = "🏷️ BHIWANI50 (₹50 OFF)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = AmberRoasted,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }

          Surface(
            shape = RoundedCornerShape(8.dp),
            color = Color(0xFFFFF4E5),
            modifier = Modifier.clickable { viewModel.applyCoupon("JABSONS10") }
          ) {
            Text(
              text = "🏷️ JABSONS10 (10% OFF)",
              fontSize = 11.sp,
              fontWeight = FontWeight.Bold,
              color = AmberRoasted,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            )
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Bhiwani Delivery Address Form
    Text(
      text = if (isHindi) "डिलीवरी विवरण (Bhiwani City)" else "Delivery Details (Bhiwani City)",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        // Customer Name
        OutlinedTextField(
          value = customerName,
          onValueChange = { viewModel.customerName.value = it },
          label = { Text("Customer Name / ग्राहक का नाम") },
          placeholder = { Text("अपना पूरा नाम लिखें") },
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_customer_name"),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SaffronPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Phone Number
        OutlinedTextField(
          value = phoneNumber,
          onValueChange = { if (it.length <= 10 && it.all { ch -> ch.isDigit() }) viewModel.phoneNumber.value = it },
          label = { Text("10-Digit Mobile Number / मोबाइल नंबर") },
          placeholder = { Text("e.g. 9812012345") },
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
          singleLine = true,
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_customer_phone"),
          shape = RoundedCornerShape(10.dp),
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SaffronPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Area Dropdown Selector
        Text(text = "Area / Locality in Bhiwani", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .clickable { areaDropdownExpanded = true }
            .padding(12.dp)
            .testTag("area_selector_box")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = selectedArea, fontSize = 13.5.sp, fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
          }

          DropdownMenu(
            expanded = areaDropdownExpanded,
            onDismissRequest = { areaDropdownExpanded = false }
          ) {
            BhiwaniData.LOCALITIES.forEach { loc ->
              DropdownMenuItem(
                text = { Text(loc) },
                onClick = {
                  viewModel.selectedArea.value = loc
                  areaDropdownExpanded = false
                }
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Street Address
        OutlinedTextField(
          value = deliveryAddress,
          onValueChange = { viewModel.deliveryAddress.value = it },
          label = { Text("Street Address / मकान नंबर, गली, नजदीकी पहचान") },
          placeholder = { Text("मकान नं., गली नं., लैंडमार्क...") },
          modifier = Modifier
            .fillMaxWidth()
            .testTag("input_delivery_address"),
          shape = RoundedCornerShape(10.dp),
          minLines = 2,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = SaffronPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.outline
          )
        )

        Spacer(modifier = Modifier.height(10.dp))

        // Delivery Slot Dropdown
        Text(text = "Delivery Slot / समय", fontSize = 12.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(4.dp))
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(10.dp))
            .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(10.dp))
            .clickable { slotDropdownExpanded = true }
            .padding(12.dp)
            .testTag("slot_selector_box")
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Text(text = selectedSlot, fontSize = 13.5.sp, fontWeight = FontWeight.Medium)
            Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = null)
          }

          DropdownMenu(
            expanded = slotDropdownExpanded,
            onDismissRequest = { slotDropdownExpanded = false }
          ) {
            BhiwaniData.DELIVERY_SLOTS.forEach { slot ->
              DropdownMenuItem(
                text = { Text(slot) },
                onClick = {
                  viewModel.selectedSlot.value = slot
                  slotDropdownExpanded = false
                }
              )
            }
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Payment Method Selection
    Text(
      text = if (isHindi) "भुगतान का तरीका (Payment Method)" else "Payment Method",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        // UPI Option
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.paymentMethod.value = "UPI" }
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          RadioButton(
            selected = paymentMethod == "UPI",
            onClick = { viewModel.paymentMethod.value = "UPI" },
            colors = RadioButtonDefaults.colors(selectedColor = SaffronPrimary)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Column {
            Text(text = "UPI (GPay / PhonePe / Paytm / QR)", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
            Text(text = "Fast online payment • Demo instant simulation", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }

        // UPI Simulated Preview
        if (paymentMethod == "UPI") {
          Box(
            modifier = Modifier
              .fillMaxWidth()
              .padding(start = 32.dp, top = 4.dp, bottom = 8.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(Color(0xFFFFF9ED))
              .border(1.dp, Color(0xFFF0E0CB), RoundedCornerShape(10.dp))
              .padding(10.dp)
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Icon(imageVector = Icons.Default.QrCode2, contentDescription = null, tint = SaffronDark, modifier = Modifier.size(28.dp))
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(text = "VPA: jabsons.bhiwani@icici", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                Text(text = "Instant demo confirmation will be verified", fontSize = 11.sp, color = VegGreen)
              }
            }
          }
        }

        Divider(modifier = Modifier.padding(vertical = 6.dp), color = Color(0xFFF1EAE0))

        // COD Option
        Row(
          modifier = Modifier
            .fillMaxWidth()
            .clickable { viewModel.paymentMethod.value = "Cash on Delivery" }
            .padding(vertical = 4.dp),
          verticalAlignment = Alignment.CenterVertically
        ) {
          RadioButton(
            selected = paymentMethod == "Cash on Delivery",
            onClick = { viewModel.paymentMethod.value = "Cash on Delivery" },
            colors = RadioButtonDefaults.colors(selectedColor = SaffronPrimary)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Column {
            Text(text = "Cash on Delivery (कैश ऑन डिलीवरी)", fontWeight = FontWeight.Bold, fontSize = 13.5.sp)
            Text(text = "डिलीवरी के समय नकद भुगतान करें", fontSize = 11.5.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          }
        }
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Bill Details
    Text(
      text = if (isHindi) "बिल विवरण" else "Bill Summary",
      fontWeight = FontWeight.Bold,
      fontSize = 15.sp,
      color = MaterialTheme.colorScheme.onSurface
    )
    Spacer(modifier = Modifier.height(8.dp))

    Card(
      colors = CardDefaults.cardColors(containerColor = Color.White),
      shape = RoundedCornerShape(16.dp),
      elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Items Subtotal", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          Text(text = "₹$subtotal", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
        }

        Spacer(modifier = Modifier.height(6.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween
        ) {
          Text(text = "Bhiwani Delivery Fee", fontSize = 13.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
          if (deliveryFee == 0) {
            Text(text = "FREE", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = VegGreen)
          } else {
            Text(text = "₹$deliveryFee", fontSize = 13.sp, fontWeight = FontWeight.SemiBold)
          }
        }

        if (discount > 0) {
          Spacer(modifier = Modifier.height(6.dp))
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
          ) {
            Text(text = "Coupon Discount ($appliedCoupon)", fontSize = 13.sp, color = VegGreen)
            Text(text = "-₹$discount", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = VegGreen)
          }
        }

        Divider(modifier = Modifier.padding(vertical = 10.dp), color = Color(0xFFF1EAE0))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
          verticalAlignment = Alignment.CenterVertically
        ) {
          Text(text = "To Pay / कुल राशि", fontSize = 16.sp, fontWeight = FontWeight.Black)
          Text(text = "₹$total", fontSize = 20.sp, fontWeight = FontWeight.Black, color = SaffronDark)
        }
      }
    }

    if (validationError != null) {
      Spacer(modifier = Modifier.height(10.dp))
      Surface(
        color = Color(0xFFFFEBEE),
        shape = RoundedCornerShape(8.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = validationError ?: "",
          color = Color(0xFFC62828),
          fontSize = 12.sp,
          fontWeight = FontWeight.Bold,
          modifier = Modifier.padding(10.dp)
        )
      }
    }

    Spacer(modifier = Modifier.height(18.dp))

    // Place Order Button
    Button(
      onClick = {
        validationError = null
        viewModel.placeOrder(
          onSuccess = { order ->
            onOrderSuccess(order)
          },
          onError = { err ->
            validationError = err
          }
        )
      },
      modifier = Modifier
        .fillMaxWidth()
        .height(52.dp)
        .testTag("submit_order_button"),
      colors = ButtonDefaults.buttonColors(containerColor = SaffronPrimary),
      shape = RoundedCornerShape(14.dp)
    ) {
      Text(
        text = if (isHindi) "ऑर्डर कन्फर्म करें • ₹$total" else "Place Demo Order • ₹$total",
        fontWeight = FontWeight.Black,
        fontSize = 16.sp,
        color = Color.White
      )
    }

    Spacer(modifier = Modifier.height(8.dp))
    Text(
      text = if (isHindi)
        "यह प्रोटोटाइप ऑर्डर आपके फोन पर दर्ज होगा। वास्तविक डिलीवरी के लिए स्टोर बैकएंड से संपर्क करें।"
      else
        "Order will be saved locally on your device with tracking. Backend connection ready for store production.",
      fontSize = 11.sp,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      lineHeight = 15.sp
    )
  }
}
