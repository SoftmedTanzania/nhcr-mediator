package tz.go.moh.him.nhcr.mediator.domain;

import java.util.List;

/**
 * Represents the response message to the EMR.
 */
public class EmrResponse {
    /**
     * The summary of the clients sent.
     */
    private Summary summary;

    /**
     * The List of failed clients mrns.
     */
    private List<FailedClientsMrn> failedClientsMrns;


    /**
     * The List of Successful clients mrns.
     */
    private List<SuccessfulClientsMrn> successfulClientsMrns;

    public Summary getSummary() {
        return summary;
    }

    public void setSummary(Summary summary) {
        this.summary = summary;
    }

    public List<FailedClientsMrn> getFailedClientsMrns() {
        return failedClientsMrns;
    }

    public void setFailedClientsMrns(List<FailedClientsMrn> FailedClientsMrns) {
        this.failedClientsMrns = FailedClientsMrns;
    }

    public List<SuccessfulClientsMrn> getSuccessfulClientsMrns() {
        return successfulClientsMrns;
    }

    public void setSuccessfulClientsMrns(List<SuccessfulClientsMrn> successfulClientsMrns) {
        this.successfulClientsMrns = successfulClientsMrns;
    }

    public static class Summary {
        /**
         * The total number of clients sent
         */
        private int totalClients;

        /**
         * The number of successful clients sent
         */
        private int successful;

        /**
         * The number of failed clients
         */
        private int failed;

        public Summary() {
        }

        public Summary(int totalClients, int successful, int failed) {
            this.totalClients = totalClients;
            this.successful = successful;
            this.failed = failed;
        }

        public int getTotalClients() {
            return totalClients;
        }

        public void setTotalClients(int totalClients) {
            this.totalClients = totalClients;
        }

        public int getSuccessful() {
            return successful;
        }

        public void setSuccessful(int successful) {
            this.successful = successful;
        }

        public int getFailed() {
            return failed;
        }

        public void setFailed(int failed) {
            this.failed = failed;
        }
    }

    public static class FailedClientsMrn {
        /**
         * The mrn of the failed client
         */
        private String mrn;

        /**
         * The error message
         */
        private String error;

        public String getMrn() {
            return mrn;
        }

        public void setMrn(String mrn) {
            this.mrn = mrn;
        }

        public String getError() {
            return error;
        }

        public void setError(String error) {
            this.error = error;
        }
    }

    public static class SuccessfulClientsMrn {
        /**
         * The mrn of the failed client
         */
        private String mrn;

        /**
         * The CR_CID message
         */
        private String crCid;

        public String getMrn() {
            return mrn;
        }

        public void setMrn(String mrn) {
            this.mrn = mrn;
        }

        public String getCrCid() {
            return crCid;
        }

        public void setCrCid(String crCid) {
            this.crCid = crCid;
        }
    }
}
