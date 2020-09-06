/**
 * 工程名:ShaVoiceService
 * 文件名:MachineState.java
 * 包   名:com.syu.sha
 * 日   期:2015年11月23日下午7:45:22
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;
import android.os.RemoteException;

/**
 *类   名:MachineState
 *功   能:TODO
 *
 *日  期:2015年11月23日 下午7:45:22
 * @author fyt
 *
 */
public interface OnMachineStateListener extends IInterface {
	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.OnMachineStateListener {
		private static final String DESCRIPTOR = "com.syu.sha.OnMachineStateListener";

		/** Construct the stub at attach it to the interface. */
		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.OnMachineStateListener interface,
		 * generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.OnMachineStateListener asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.OnMachineStateListener))) {
				return ((kun.kt.opencam.ipc.OnMachineStateListener) iin);
			}
			return new kun.kt.opencam.ipc.OnMachineStateListener.Stub.Proxy(obj);
		}

		@Override
		public android.os.IBinder asBinder() {
			return this;
		}

		@Override
		public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags)
				throws RemoteException {
			switch (code) {
			case INTERFACE_TRANSACTION: {
				reply.writeString(DESCRIPTOR);
				return true;
			}
			case TRANSACTION_onStateChanged: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0, _arg1;
				_arg0 = data.readInt();
				_arg1 = data.readInt();
				this.onStateChanged(_arg0, _arg1);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_onHasCarRadio: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				this.onHasCarRadio(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_onBlackScreen: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				this.onBlackScreen(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_onReversing: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				this.onReversing(_arg0);
				reply.writeNoException();
				return true;
			}
			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.OnMachineStateListener {
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
			public void onStateChanged(int state, int sleep) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(state);
					_data.writeInt(sleep);
					mRemote.transact(Stub.TRANSACTION_onStateChanged, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void onHasCarRadio(int has) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(has);
					mRemote.transact(Stub.TRANSACTION_onHasCarRadio, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void onBlackScreen(int black) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(black);
					mRemote.transact(Stub.TRANSACTION_onBlackScreen, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void onReversing(int reversing) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(reversing);
					mRemote.transact(Stub.TRANSACTION_onReversing, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_onStateChanged = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
		static final int TRANSACTION_onHasCarRadio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
		static final int TRANSACTION_onBlackScreen = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
		static final int TRANSACTION_onReversing = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
	}
	/**
	 * @param state 1 - 机器可以使用（机器不在黑屏/待机/关机状态） 否则机器不可使用
	 */
	public void onStateChanged(int state, int sleep) throws RemoteException;
	/**
	 * @param has -- true 有原车收音机  false -- 无原车收音机
	 */
	public void onHasCarRadio(int has) throws RemoteException;
	/**
	 * @param black 1-黑屏  0 - 亮屏
	 */
	public void onBlackScreen(int black) throws RemoteException;
	/**
	 * @param reversing 1-倒车  0-非倒车
	 * @throws RemoteException
	 */
	public void onReversing(int reversing)throws RemoteException;
}
