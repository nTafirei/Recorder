<%@ include file="/WEB-INF/tags/taglibs.jsp" %>
<%@taglib prefix="security" uri="http://www.providencebehavior.com/security.tld" %>
       <!-- Footer -->
       <section id="footer">
           <div class="container">
               <header>
                   <h3>Questions or comments? <strong>Get in touch:</strong></h3>
               </header>
               <div class="row">
                       <section>
                           <d:form method="post" action="/web/message" id="contact_form" name="contact_form">
                               <div class="row gtr-50">
                                   <div class="col-6 col-12-small">
                                       <input name="name" placeholder="Name" type="text"/>
                                   </div>
                                   <div class="col-6 col-12-small">
                                       <input name="email" placeholder="Email" type="text"/>
                                   </div>
                                   <div class="col-6 col-12-large">
                                       <input name="subject" placeholder="Subject" type="text"/>
                                   </div>
                                   <div class="col-12">
                                       <textarea name="message" id="message" placeholder="Message"></textarea>
                                   </div>
                                   <div class="col-12">
                                       <a href="#" class="form-button-submit button icon solid fa-envelope" onclick="document.contact_form.submit();">Send
                                           Message</a>
                                   </div>
                               </div>
                           </d:form>
                       </section>
                       <section>
                           <p>You can also contact us using the following</p>
                           <div class="row">
                               <div class="col-6 col-12-large">
                                   <ul class="icons">
                                       <li class="icon solid fa-home">
                                           3535 Tynwald North<br/>
                                           Harare<br/>
                                           Zimbabwe
                                       </li>
                                       <li class="icon solid fa-phone">
                                           <a href="tel:+263712571508">(+263) 71 257 1508</a><br/>
                                       <li class="icon solid fa-envelope">
                                           <a href="mailto:admin@recorder.co.zw">admin@recorder.co.zw</a>
                                       </li>
                                   </ul>
                               </div>
                               <div class="col-6 col-12-small">
                                    <d:link href="/web/help">Help</d:link>
                               </div>
                               <div class="col-6 col-12-small">
                                    <d:link href="/web/terms">Terms and Conditions</d:link>
                               </div>
                           </div>
                       </section>
               </div>
           </div>
           <div id="copyright" class="container">
               <ul class="links">
                   <li>${actionBean.copyRight}</li>
                   <li>Design: <a href="http://html5up.net">HTML5 UP</a></li>
               </ul>
           </div>
       </section>
