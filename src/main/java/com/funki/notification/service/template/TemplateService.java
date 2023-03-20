package com.test.notification.service.template;

import java.text.SimpleDateFormat;

import com.test.notification.dto.ContactUsRequest;
import com.test.notification.dto.CrmRequest;
import com.test.notification.dto.EmailQueueDTO;
import com.test.notification.dto.LoanApplicationRequest;
import com.test.notification.dto.SubscriptionRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring5.SpringTemplateEngine;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TemplateService {
        private final SpringTemplateEngine templateEngine;

        @Value("${spring.mail.addresses.from}")
        private String fromAddress;

        @Value("${spring.mail.addresses.replyTo}")
        private String replyToddress;

        @Value("${test.subscription.recipient}")
        private String subscriptionRecipient;

        @Value("${test.subscription.subject}")
        private String subscriptionSubject;

        @Value("${test.loanApplication.recipient}")
        private String loanApplicationRecipient;

        @Value("${test.loanApplication.subject}")
        private String loanApplicationSubject;

        @Value("${test.loanApplication.downloadUrl}")
        private String loanApplicationDownloadUrl;

        @Value("${test.contactUs.recipient}")
        private String contactUsRecipient;

        @Value("${test.contactUs.subject}")
        private String contactUsSubject;

        @Value("${test.crmNotification.subject}")
        private String crmSubject;

        @Value("${test.crmNotification.loanInformationPage}")
        private String crmUrl;

        @Value("${test.crmNotification.from}")
        private String crmFromAddress;

        @Value("${test.crmNotification.replyTo}")
        private String crmReplyToAddress;

        public EmailQueueDTO generateEmail(SubscriptionRequest subscriptionInfo) {
                Context context = new Context();
                context.setVariable("subscriberName", subscriptionInfo.getSubscriberName());
                context.setVariable("subscriberPhone", subscriptionInfo.getSubscriberPhone());

                return EmailQueueDTO.builder().fromAddress(fromAddress).replyToAddress(replyToddress)
                                .subject(subscriptionSubject).recipient(subscriptionRecipient)
                                .textContent(templateEngine.process("subscription-email.txt", context))
                                .htmlContent(templateEngine.process("subscription-email.html", context)).build();
        }

        public EmailQueueDTO generateEmail(LoanApplicationRequest loanApplicationInfo) {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Context context = new Context();
                context.setVariable("id", loanApplicationInfo.getId());
                context.setVariable("status", loanApplicationInfo.getStatus());
                context.setVariable("loanAmount", loanApplicationInfo.getLoanAmount());
                context.setVariable("repayCycle", loanApplicationInfo.getRepayCycle());
                context.setVariable("loanPurpose", loanApplicationInfo.getLoanPurpose());
                context.setVariable("salutation", loanApplicationInfo.getSalutation());
                context.setVariable("engName", loanApplicationInfo.getEngName());
                context.setVariable("hkid", loanApplicationInfo.getHkid());
                if (loanApplicationInfo.getDob() != null) {
                        context.setVariable("dob", sdf.format(loanApplicationInfo.getDob()));
                }
                context.setVariable("flat", loanApplicationInfo.getFlat());
                context.setVariable("floor", loanApplicationInfo.getFloor());
                context.setVariable("block", loanApplicationInfo.getBlock());
                context.setVariable("building", loanApplicationInfo.getBuilding());
                context.setVariable("street", loanApplicationInfo.getStreet());
                context.setVariable("area", loanApplicationInfo.getArea());
                context.setVariable("monthlyIncome", loanApplicationInfo.getMonthlyIncome());
                context.setVariable("nationality", loanApplicationInfo.getNationality());
                context.setVariable("mobile", loanApplicationInfo.getMobile());
                context.setVariable("email", loanApplicationInfo.getEmail());
                context.setVariable("position", loanApplicationInfo.getPosition());
                context.setVariable("livingPeriod", loanApplicationInfo.getLivingPeriod());
                context.setVariable("companyName", loanApplicationInfo.getCompanyName());
                context.setVariable("companyPhone", loanApplicationInfo.getCompanyPhone());
                context.setVariable("companyFlat", loanApplicationInfo.getCompanyFlat());
                context.setVariable("companyFloor", loanApplicationInfo.getCompanyFloor());
                context.setVariable("companyBlock", loanApplicationInfo.getCompanyBlock());
                context.setVariable("companyBuilding", loanApplicationInfo.getCompanyBuilding());
                context.setVariable("companyStreet", loanApplicationInfo.getCompanyStreet());
                context.setVariable("companyArea", loanApplicationInfo.getCompanyArea());
                context.setVariable("department", loanApplicationInfo.getDepartment());
                context.setVariable("jobNature", loanApplicationInfo.getJobNature());
                if (loanApplicationInfo.getEmploymentYear() != null) {
                        context.setVariable("employmentYear", loanApplicationInfo.getEmploymentYear().intValue());
                }
                if (loanApplicationInfo.getEmploymentMonth() != null) {
                        context.setVariable("employmentMonth", loanApplicationInfo.getEmploymentMonth().intValue());
                }
                context.setVariable("otherIncome", loanApplicationInfo.getOtherIncome());
                context.setVariable("maritalStatus", loanApplicationInfo.getMaritalStatus());
                context.setVariable("livingStatus", loanApplicationInfo.getLivingStatus());
                context.setVariable("housingExpenses", loanApplicationInfo.getHousingExpenses());
                if (loanApplicationInfo.getHkidFileName() != null
                                && !loanApplicationInfo.getHkidFileName().equals("")) {
                        context.setVariable("hkidFileName", "<a href=\"" + loanApplicationDownloadUrl
                                        + loanApplicationInfo.getHkidFileName() + "\">Download</a>");
                } else {
                        context.setVariable("hkidFileName", "N/A");
                }
                if (loanApplicationInfo.getAddressProofFileName() != null
                                && !loanApplicationInfo.getAddressProofFileName().equals("")) {
                        context.setVariable("addressProofFileName", "<a href=\"" + loanApplicationDownloadUrl
                                        + loanApplicationInfo.getAddressProofFileName() + "\">Download</a>");
                } else {
                        context.setVariable("addressProofFileName", "N/A");
                }
                if (loanApplicationInfo.getIncomeProofFileName() != null
                                && !loanApplicationInfo.getIncomeProofFileName().equals("")) {
                        context.setVariable("incomeProofFileName", "<a href=\"" + loanApplicationDownloadUrl
                                        + loanApplicationInfo.getIncomeProofFileName() + "\">Download</a>");
                } else {
                        context.setVariable("incomeProofFileName", "N/A");

                }
                if (loanApplicationInfo.getAccountProofFileName() != null
                                && !loanApplicationInfo.getAccountProofFileName().equals("")) {
                        context.setVariable("accountProofFileName", "<a href=\"" + loanApplicationDownloadUrl
                                        + loanApplicationInfo.getAccountProofFileName() + "\">Download</a>");
                } else {
                        context.setVariable("accountProofFileName", "N/A");

                }
                context.setVariable("tuConcent", loanApplicationInfo.getTuConsent());
                context.setVariable("personalInfoConcent", loanApplicationInfo.getPersonalInfoConsent());
                context.setVariable("marketingCommunicationConcent",
                                loanApplicationInfo.getMarketingCommunicationConsent());

                return EmailQueueDTO.builder().fromAddress(fromAddress).replyToAddress(replyToddress)
                                .subject(loanApplicationSubject).recipient(loanApplicationRecipient)
                                .textContent(templateEngine.process("loan-application-email.txt", context))
                                .htmlContent(templateEngine.process("loan-application-email.html", context)).build();
        }

        public EmailQueueDTO generateEmail(ContactUsRequest contactUsInfo) {
                Context context = new Context();
                context.setVariable("name", contactUsInfo.getName());
                context.setVariable("email", contactUsInfo.getEmail());
                context.setVariable("contactNumber", contactUsInfo.getContactNumber());
                context.setVariable("message", contactUsInfo.getMessage());

                return EmailQueueDTO.builder().fromAddress(fromAddress).replyToAddress(replyToddress)
                                .subject(contactUsSubject).recipient(contactUsRecipient)
                                .textContent(templateEngine.process("contactus-email.txt", context))
                                .htmlContent(templateEngine.process("contactus-email.html", context)).build();
        }

        public EmailQueueDTO generateEmail(CrmRequest crmInfo) {
                String subject = String.format("%s %s - %s from %s", crmSubject, crmInfo.getFollowUp(),
                                crmInfo.getApplyNo(), crmInfo.getAssigner());
                String loanUrl = String.format("%s%s", crmUrl, crmInfo.getApplyNo());
                Context context = new Context();
                context.setVariable("assignee", crmInfo.getAssignee());
                context.setVariable("assigner", crmInfo.getAssigner());
                context.setVariable("recordDate", crmInfo.getRecordDate());
                context.setVariable("followUp", crmInfo.getFollowUp());
                context.setVariable("content", crmInfo.getContent());
                context.setVariable("url", loanUrl);
                return EmailQueueDTO.builder().fromAddress(crmFromAddress).replyToAddress(crmReplyToAddress)
                                .subject(subject).recipient(crmInfo.getRecipient())
                                .textContent(templateEngine.process("crm-notification.txt", context))
                                .htmlContent(templateEngine.process("crm-notification.html", context)).build();
        }
}