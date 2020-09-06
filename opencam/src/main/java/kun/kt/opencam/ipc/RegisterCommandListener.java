/**
 * 工程名:ShaVoiceService
 * 文件名:RegisterCommandListener.java
 * 包   名:com.syu.sha.ipc
 * 日   期:2015年12月16日上午10:11:44
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;

/**
 * 类 名:RegisterCommandListener 功 能:TODO
 *
 * 日 期:2015年12月16日 上午10:11:44
 * 
 * @author fyt
 *
 */
public interface RegisterCommandListener extends IInterface {
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.RegisterCommandListener {
		private static final String DESCRIPTOR = "com.syu.sha.RegisterCommandListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.RegisterCommandListener
		 * interface, generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.RegisterCommandListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.RegisterCommandListener))) {
				return ((kun.kt.opencam.ipc.RegisterCommandListener) iin);
			}
			return new kun.kt.opencam.ipc.RegisterCommandListener.Stub.Proxy(obj);
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
			case TRANSACTION_onRegisterCammand: {
				data.enforceInterface(DESCRIPTOR);
				String[] _arg0;
				int _arg0_length = data.readInt();
				if ((_arg0_length < 0)) {
					_arg0 = null;
				} else {
					_arg0 = new String[_arg0_length];
				}
				String _arg1;
				_arg1 = data.readString();
				this.onRegisterCammand(_arg0, _arg1);
				reply.writeNoException();
				reply.writeStringArray(_arg0);
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.RegisterCommandListener {
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
			public void onRegisterCammand(String[] cmdNames, String cmd) throws android.os.RemoteException {

				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					if ((cmdNames == null)) {
						_data.writeInt(-1);
					} else {
						_data.writeInt(cmdNames.length);
					}
					_data.writeString(cmd);
					mRemote.transact(Stub.TRANSACTION_onRegisterCammand, _data, _reply, 0);
					_reply.readException();
					_reply.readStringArray(cmdNames);
				} finally {
					_reply.recycle();
					_data.recycle();
				}

			}
		}

		static final int TRANSACTION_onRegisterCammand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}

	public void onRegisterCammand(String[] cmdNames, String cmd) throws android.os.RemoteException;
}
