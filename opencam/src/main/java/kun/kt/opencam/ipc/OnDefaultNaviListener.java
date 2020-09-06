/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: /home/fyt/workspace/ShaVoiceService/src/com/syu/sha/ipc/OnDefaultNaviListener.aidl
 */
package kun.kt.opencam.ipc;

public interface OnDefaultNaviListener extends android.os.IInterface {
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnDefaultNaviListener {
		private static final String DESCRIPTOR = "kun.kt.opencam.ipc.OnDefaultNaviListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an kun.kt.opencam.ipc.OnDefaultNaviListener
		 * interface, generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnDefaultNaviListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnDefaultNaviListener))) {
				return ((kun.kt.opencam.ipc.OnDefaultNaviListener) iin);
			}
			return new kun.kt.opencam.ipc.OnDefaultNaviListener.Stub.Proxy(obj);
		}

		@Override
		public android.os.IBinder asBinder() {
			return this;
		}

		@Override
		public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
				throws android.os.RemoteException {
			switch (code) {
			case INTERFACE_TRANSACTION: {
				reply.writeString(DESCRIPTOR);
				return true;
			}
			case TRANSACTION_onDefaultNaviChange: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				this.onDefaultNaviChange(_arg0);
				reply.writeNoException();
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnDefaultNaviListener {
			private android.os.IBinder mRemote;

			Proxy(android.os.IBinder remote) {
				mRemote = remote;
			}

			@Override
			public android.os.IBinder asBinder() {
				return mRemote;
			}

			public String getInterfaceDescriptor() {
				return DESCRIPTOR;
			}

			@Override
			public void onDefaultNaviChange(String name) throws android.os.RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(name);
					mRemote.transact(Stub.TRANSACTION_onDefaultNaviChange, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onDefaultNaviChange = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}

	public void onDefaultNaviChange(String name) throws android.os.RemoteException;
}
