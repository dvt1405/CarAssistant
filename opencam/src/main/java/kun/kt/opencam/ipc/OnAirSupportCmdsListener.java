/**
 * 工程名:ShaVoiceService
 * 文件名:OnAirSupportCmdsListener.java
 * 包   名:com.syu.sha.ipc
 * 日   期:2016年4月7日上午10:21:14
 * 作   者:fyt 
 * Copyright (c) 2016, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

/**
 * 类 名:OnAirSupportCmdsListener 功 能:TODO
 *
 * 日 期:2016年4月7日 上午10:21:14
 * 
 * @author fyt
 *
 */

public interface OnAirSupportCmdsListener extends android.os.IInterface {
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnAirSupportCmdsListener {
		private static final String DESCRIPTOR = "kun.kt.opencam.ipc.OnAirSupportCmdsListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an
		 * kun.kt.opencam.ipc.OnAirSupportCmdsListener interface, generating a
		 * proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnAirSupportCmdsListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnAirSupportCmdsListener))) {
				return ((kun.kt.opencam.ipc.OnAirSupportCmdsListener) iin);
			}
			return new kun.kt.opencam.ipc.OnAirSupportCmdsListener.Stub.Proxy(obj);
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
			case TRANSACTION_updateAirCmds: {
				data.enforceInterface(DESCRIPTOR);
				int[] _arg0;
				_arg0 = data.createIntArray();
				this.updateAirCmds(_arg0);
				reply.writeNoException();
				reply.writeIntArray(_arg0);
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnAirSupportCmdsListener {
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
			public void updateAirCmds(int[] cmds) throws android.os.RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeIntArray(cmds);
					mRemote.transact(Stub.TRANSACTION_updateAirCmds, _data, _reply, 0);
					_reply.readException();
					_reply.readIntArray(cmds);
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_updateAirCmds = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
	}

	public void updateAirCmds(int[] cmds) throws android.os.RemoteException;
}
