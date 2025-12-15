<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Home">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper" style="background-color: #fdfdfd; border-radius: 10px 10px 10px 10px;overflow:hidden;">
            <div class="container">
                <div id="content">
                    <strong>What is the PayStream platform?</strong><br/>
                    PayStream allows you to buy pre-paid tokens and vouchers for ZESA, water, fuel and LNG (gas).
                    <br/>
                    You can purchase these vouchers for yourself and friends and family from anywhere in the world.
                    <br/>
                    Our platform allows you buy vouchers using any smart phone, USSD (mbudzi or chidhinha phone), as well as
                    using our website.
                    <br/>
                    We accept all mobile wallets registered in Zimbabwe.<br/>
                    We accept all currencies in use in Zimbabwe.<br/>
                    We accept all bank cards used in Zimbabwe.<br/>
                    Further, we accept all international Visa and Mastercard cards.<br/>
                    Once you have purchased your voucher, you will be sent the voucher by SMS and email.<br/>
                    You then simply present the voucher number to the appropriate merchant as noted below.

<br/><br/>
                    <strong>Benefits:</strong><br/>
                    The PayStream service allows you to purchase services without needing to present or transfer cash.<br/>
                    This is especially beneficial when you need to pay products for friends or family without having to send money.<br/>
                    This service is especially beneficial to those in the diaspora as it allows you to save a great deal
                    on transactions fees associated with money transfer services.
                    <br/><br/>
                    <strong>Fulfillment:</strong><br/>
                    For ZESA: simply enter the voucher number into your pre-paid metre.<br/>
                    For water: simple enter the voucher number into your pre-paid water metre.<br/>
                    For fuel: simply present the voucher or voucher number to the fuel attendant.
                    <a href="">See all fuel stations nearby accepting our vouchers</a>
                    <br/>
                    For LNG (gas), simply present the voucher or voucher number to an authorised agent.
                    <a href="">See all agents nearby accepting our vouchers</a>

                </div>
            </div>
        </div>
        <div>
            <s:errors/>
        </div>
    </s:layout-component>
</s:layout-render>

