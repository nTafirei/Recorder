<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<s:layout-render name="/WEB-INF/jsp/layout.jsp" title="Message Sent">

    <s:layout-component name="head">
    </s:layout-component>

    <s:layout-component name="contents">
        <!-- Main -->
        <div class="container">
            <div class="row aln-center">
                <div class="col-4 col-6-medium col-12-large">
                    <section>
                        <s:errors/>
                        <c:if test="${!actionBean.hasErrors}">
                              <strong>Your message has been sent and will be processed in due time.</strong>
                        </c:if>
                    </section>
                </div>
             </div>
        </div>
    </s:layout-component>
</s:layout-render>
