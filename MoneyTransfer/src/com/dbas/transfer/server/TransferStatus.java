package com.dbas.transfer.server;
public enum TransferStatus {
		COMPLETE("complete"), INCOMPLETE("incomplete");

		private String status;

		private TransferStatus(String status) {
			this.status = status;
		}

		@Override
		public String toString() {
			return status;
		}
	}