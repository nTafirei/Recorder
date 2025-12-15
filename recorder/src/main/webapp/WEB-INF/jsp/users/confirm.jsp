<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>

<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="MaroTech - Register">
    <s:layout-component name="head">
    </s:layout-component>
    <s:layout-component name="contents">

        <div id="main-wrapper main-contents">
            <div class="container">
                <div id="content">
                    <table class="alternating">
                        <tr>
                            <td>
                                <fmt:message key="pleaseconfirmregistrationlabel"/>
                            </td>
                        </tr>
                    </table>
                </div>
            </div>
        </div>
    </s:layout-component>
</s:layout-render>
