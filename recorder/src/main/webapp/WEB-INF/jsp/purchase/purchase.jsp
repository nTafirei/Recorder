<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Purchase ${actionBean.product.name}">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">

                    <s:errors/>
                       <s:form action="/web/purchase" name="pForm" id="pForm" method="post">
                         <input type="hidden" name="_eventName" id="_eventName" value="save"/>
                         <input type="hidden" name="product" id="merchant" value="${actionBean.product.id}"/>

                        <table width="100%">
                            <tr>
                                <td align="center" colspan="3">
                                    <fmt:message key="buylabel"/> ${actionBean.product.name}
                                </td>
                            </tr>
                        </table>
                        <table class="alternating">
                                <tbody>
                                <tr>
                                    <td>
                                        <fmt:message key="buyermobilephonelabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="buyerCountryCode" id="buyerCountryCode" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectcountrycodelabel"/></s:option>
                                                   <c:forEach items="${actionBean.countryCodes}" var="countryCode"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.buyerCountryCode !=null &&
                                                             actionBean.buyerCountryCode == countryCode}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${countryCode}" ${selectedValue}/>
                                                      ${countryCode}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                        <d:text style="background-color:#F0E68C" name="buyerMobileNumber" value="${actionBean.buyerMobileNumber}"
                                        id="buyerMobileNumber"/>
                                    </td>
                                </tr>

                                <tr>
                                    <td>
                                        <fmt:message key="beneficiarymobilelabel"/>
                                    </td>
                                    <td nowrap="nowrap">
                                        (+${actionBean.defaultCountryCode})
                                        <d:text style="background-color:#F0E68C" name="beneficiaryMobileNumber" id="beneficiaryMobileNumber"/>
                                    </td>
                                </tr>
                                <c:if test="${actionBean.product.requiresMetre}">
                                    <tr>
                                        <td>
                                            <fmt:message key="metrenumberlabel"/>
                                        </td>
                                        <td>
                                            <d:text style="background-color:#F0E68C" name="meterNumber" id="meterNumber"/>
                                        </td>
                                    </tr>
                                </c:if>
                                <tr valign="top">
                                    <td nowrap="nowrap">
                                        <fmt:message key="paymenttypelabel"/>
                                    </td>
                                     <td>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="paymentMethod" id="paymentMethod" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectpaymentmethodlabel"/></s:option>
                                                   <c:forEach items="${actionBean.paymentMethods}" var="paymentMethod"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.paymentMethod !=null &&
                                                             actionBean.paymentMethod.id == paymentMethod.id}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${paymentMethod.id}" ${selectedValue}/>
                                                      ${paymentMethod.name}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                     </td>
                                 </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="accountorcardnumberlabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="cardNumber" id="cardNumber"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="accountnamelabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="accountName" id="accountName"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="paymentpinnumberlabel"/>
                                    </td>
                                    <td>
                                        <d:password style="background-color:#F0E68C" name="cardPin" id="cardPin"/>
                                    </td>
                                </tr>
                                <tr valign="top">
                                    <td nowrap="nowrap">
                                        <fmt:message key="currencylabel"/>
                                    </td>
                                     <td>
                                             <c:set var="selectedValue" value=""/>
                                             <s:select name="currency" id="currency" style="background-color:#F0E68C">
                                                   <s:option value="">
                                                   <fmt:message key="selectcurrencylabel"/></s:option>
                                                   <c:forEach items="${actionBean.currencies}" var="currency"
                                                              varStatus="loopStatus">

                                                       <c:if test="${actionBean.currency !=null &&
                                                             actionBean.currency == currency}">
                                                         <c:set var="selectedValue" value="selected"/>
                                                      </c:if>
                                                       <option value="${currency}" ${selectedValue}/>
                                                      ${currency}
                                                      <c:set var="selectedValue" value=""/>
                                                   </c:forEach>
                                             </s:select>
                                     </td>
                                 </tr>
                                <tr>
                                    <td>
                                        <fmt:message key="amountlabel"/>
                                    </td>
                                    <td>
                                        <d:text style="background-color:#F0E68C" name="amount"/>
                                    </td>
                                </tr>
                                <tr>
                                    <td colspan="2" align="right">
                                        <fmt:message key="submitlabel" var="submitlabel"/>
                                        <d:submit class="small" name="save" value="${submitlabel}"/>
                                    </td>
                                </tr>
                            <tbody>
                        </table>
                      </s:form>
                      <script language="javascript">
                      $(document).ready(function() {
                            var paymentMethod = document.getElementById('paymentMethod');
                            const cardNumber = document.getElementById('cardNumber');
                            const accountName = document.getElementById('accountName');
                            const cardPin = document.getElementById('cardPin');
                            paymentMethod.addEventListener('change', function() {
                              // Find the input text field by its name attribute and disable it
                                if (paymentMethod) {
                                    if (paymentMethod.value == '${actionBean.defaultPaymentMethod.id}') {
                                      cardNumber.value = '';
                                      accountName.value = '';
                                      cardPin.value = '';
                                      cardNumber.disabled = true;
                                      accountName.disabled = true;
                                      cardPin.disabled = true;
                                      cardPin.style.backgroundColor = 'white';
                                      accountName.style.backgroundColor = 'white';
                                      cardNumber.style.backgroundColor = 'white';
                                    }
                                  }else{
                                      cardNumber.disabled = false;
                                      accountName.disabled = false;
                                      cardPin.disabled = false;
                                      cardPin.style.backgroundColor = '#F0E68C';
                                      accountName.style.backgroundColor = '#F0E68C';
                                      cardNumber.style.backgroundColor = '#F0E68C';
                                 }
                            });
                          });
                      </script>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
