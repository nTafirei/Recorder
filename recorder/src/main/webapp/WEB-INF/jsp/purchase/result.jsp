<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Purchase Result">

    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <fmt:message key="transactionsucccessful"/>
                    <br/>
                    <fmt:message key="vouchernumberis"/> <strong>${actionBean.voucher.voucherNumber}</strong>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
