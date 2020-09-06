/**
 * 工程名:ShaVoiceService
 * 文件名:ITransitService.java
 * 包   名:com.syu.sha
 * 日   期:2015年11月23日下午4:13:55
 * 作   者:fyt 
 * Copyright (c) 2015, kexuan52@yeah.net All Rights Reserved.
 *
 */
package kun.kt.opencam.ipc;

import android.os.IInterface;
import android.os.RemoteException;

import java.util.List;

/**
 * 类 名:ITransitService 功 能:TODO
 *
 * 日 期:2015年11月23日 下午4:13:55
 * 
 * @author fyt
 *
 */
public interface ITransitService extends IInterface {

	/** Local-side IPC implementation stub class. */
	public static abstract class Stub extends android.os.Binder implements kun.kt.opencam.ipc.ITransitService {
		private static final String DESCRIPTOR = "com.syu.sha.ITransitService";

		public Stub() {
			this.attachInterface(this, DESCRIPTOR);
		}

		/**
		 * Cast an IBinder object into an com.syu.sha.ITransitService interface,
		 * generating a proxy if needed.
		 */
		public static kun.kt.opencam.ipc.ITransitService asInterface(android.os.IBinder obj) {
			if ((obj == null)) {
				return null;
			}
			IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
			if (((iin != null) && (iin instanceof kun.kt.opencam.ipc.ITransitService))) {
				return ((kun.kt.opencam.ipc.ITransitService) iin);
			}
			return new kun.kt.opencam.ipc.ITransitService.Stub.Proxy(obj);
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
			case TRANSACTION_openApp: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				this.openApp(_arg0);
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_closeApp: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				this.closeApp(_arg0);
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_setRadioFreq: {
				data.enforceInterface(DESCRIPTOR);
				float _arg0;
				_arg0 = data.readFloat();
				this.setRadioFreq(_arg0);
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_setOnBluetoothPhoneListener: {
				data.enforceInterface(DESCRIPTOR);
				OnBluetoothPhoneListener listener;
				listener = OnBluetoothPhoneListener.Stub.asInterface(data.readStrongBinder());
				this.setOnBluetoothPhoneListener(listener);
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_setOnContactsListener: {
				data.enforceInterface(DESCRIPTOR);
				OnContactsListener listener;
				listener = OnContactsListener.Stub.asInterface(data.readStrongBinder());
				this.setOnContactsListener(listener);
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_answer: {
				data.enforceInterface(DESCRIPTOR);
				this.answer();
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_hang: {
				data.enforceInterface(DESCRIPTOR);
				this.hang();
				reply.writeNoException();
				return true;
			}
			case TRANSACTION_dial: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				this.dial(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_setOnMainChannleListener: {
				data.enforceInterface(DESCRIPTOR);
				OnMainChannleListener listener;
				listener = OnMainChannleListener.Stub.asInterface(data.readStrongBinder());
				this.setOnMainChannleListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_setOnMachineStateListener: {
				data.enforceInterface(DESCRIPTOR);
				OnMachineStateListener listener;
				listener = OnMachineStateListener.Stub.asInterface(data.readStrongBinder());
				this.setOnMachineStateListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_requestAudio: {
				data.enforceInterface(DESCRIPTOR);
				this.requestAudio();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_releaseAudio: {
				data.enforceInterface(DESCRIPTOR);
				this.releaseAudio();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_volumeUp: {
				data.enforceInterface(DESCRIPTOR);
				this.volumeUp();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_volumeDown: {
				data.enforceInterface(DESCRIPTOR);
				this.volumeDown();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_next: {
				data.enforceInterface(DESCRIPTOR);
				this.next();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_prev: {
				data.enforceInterface(DESCRIPTOR);
				this.prev();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_play: {
				data.enforceInterface(DESCRIPTOR);
				this.play();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_pause: {
				data.enforceInterface(DESCRIPTOR);
				this.pause();
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_addOnCammandListener: {
				data.enforceInterface(DESCRIPTOR);
				OnCommandListener listener;
				listener = OnCommandListener.Stub.asInterface(data.readStrongBinder());
				this.addOnCommandListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_removeOnCammandListener: {
				data.enforceInterface(DESCRIPTOR);
				OnCommandListener listener;
				listener = OnCommandListener.Stub.asInterface(data.readStrongBinder());
				this.removeOnCommandListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_removeOnMusicDataListener: {
				data.enforceInterface(DESCRIPTOR);
				OnMusicDataListener listener;
				listener = OnMusicDataListener.Stub.asInterface(data.readStrongBinder());
				this.removeOnMusicDataListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_addOnMusicDataListener: {
				data.enforceInterface(DESCRIPTOR);
				OnMusicDataListener listener;
				listener = OnMusicDataListener.Stub.asInterface(data.readStrongBinder());
				this.addOnMusicDataListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_sendMusicData: {
				data.enforceInterface(DESCRIPTOR);
				List<Song> songs;
				songs = data.readArrayList(Song.class.getClassLoader());
				this.sendMusicData(songs);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_command: {
				data.enforceInterface(DESCRIPTOR);
				String _arg0;
				_arg0 = data.readString();
				this.command(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_addRegisterCommandListener: {
				data.enforceInterface(DESCRIPTOR);
				RegisterCommandListener listener;
				listener = RegisterCommandListener.Stub.asInterface(data.readStrongBinder());
				this.addRegisterCommandListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_removeRegisterCommandListener: {
				data.enforceInterface(DESCRIPTOR);
				RegisterCommandListener listener;
				listener = RegisterCommandListener.Stub.asInterface(data.readStrongBinder());
				this.removeRegisterCommandListener(listener);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_registerCommand: {
				data.enforceInterface(DESCRIPTOR);
				String[] _arg0;
				_arg0 = data.createStringArray();
				String _arg1;
				_arg1 = data.readString();
				this.registerCommand(_arg0, _arg1);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_addOnDefaultNaviListener: {
				data.enforceInterface(DESCRIPTOR);
				kun.kt.opencam.ipc.OnDefaultNaviListener _arg0;
				_arg0 = kun.kt.opencam.ipc.OnDefaultNaviListener.Stub.asInterface(data.readStrongBinder());
				this.addOnDefaultNaviListener(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_removeOnDefaultNaviListener: {
				data.enforceInterface(DESCRIPTOR);
				kun.kt.opencam.ipc.OnDefaultNaviListener _arg0;
				_arg0 = kun.kt.opencam.ipc.OnDefaultNaviListener.Stub.asInterface(data.readStrongBinder());
				this.removeOnDefaultNaviListener(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_sendAirCmd: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				int[] _arg1;
				_arg1 = data.createIntArray();
				this.sendAirCmd(_arg0, _arg1);
				reply.writeNoException();
				reply.writeIntArray(_arg1);
				return true;
			}

			case TRANSACTION_addOnAirSupportCmdsListener: {
				data.enforceInterface(DESCRIPTOR);
				kun.kt.opencam.ipc.OnAirSupportCmdsListener _arg0;
				_arg0 = kun.kt.opencam.ipc.OnAirSupportCmdsListener.Stub.asInterface(data.readStrongBinder());
				this.addOnAirSupportCmdsListener(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_removeOnAirSupportCmdsListener: {
				data.enforceInterface(DESCRIPTOR);
				kun.kt.opencam.ipc.OnAirSupportCmdsListener _arg0;
				_arg0 = kun.kt.opencam.ipc.OnAirSupportCmdsListener.Stub.asInterface(data.readStrongBinder());
				this.removeOnAirSupportCmdsListener(_arg0);
				reply.writeNoException();
				return true;
			}

			case TRANSACTION_sendCmd: {
				data.enforceInterface(DESCRIPTOR);
				int _arg0;
				_arg0 = data.readInt();
				int _arg1;
				_arg1 = data.readInt();
				int[] _arg2;
				_arg2 = data.createIntArray();
				this.sendCmd(_arg0, _arg1, _arg2);
				reply.writeNoException();
				reply.writeIntArray(_arg2);
				return true;
			}

			}
			return super.onTransact(code, data, reply, flags);
		}

		private static class Proxy implements kun.kt.opencam.ipc.ITransitService {
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

			// 打开应用
			@Override
			public void openApp(String pkg) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(pkg);
					mRemote.transact(Stub.TRANSACTION_openApp, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			// 关闭应用
			@Override
			public void closeApp(String pkg) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(pkg);
					mRemote.transact(Stub.TRANSACTION_closeApp, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 设置收音机频率

			@Override
			public void setRadioFreq(float freq) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeFloat(freq);
					mRemote.transact(Stub.TRANSACTION_setRadioFreq, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 设置蓝牙状态监听

			@Override
			public void setOnBluetoothPhoneListener(OnBluetoothPhoneListener listener)
					throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_setOnBluetoothPhoneListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 设置联系人更新

			@Override
			public void setOnContactsListener(OnContactsListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_setOnContactsListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 接听

			@Override
			public void answer() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_answer, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 挂断

			@Override
			public void hang() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_hang, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
			// 拨号

			@Override
			public void dial(String number) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(number);
					mRemote.transact(Stub.TRANSACTION_dial, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void setOnMainChannleListener(OnMainChannleListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_setOnMainChannleListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void setOnMachineStateListener(OnMachineStateListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_setOnMachineStateListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void requestAudio() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_requestAudio, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void releaseAudio() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_releaseAudio, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void volumeUp() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_volumeUp, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void volumeDown() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_volumeDown, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void next() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_next, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void prev() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_prev, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void play() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_play, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void pause() throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					mRemote.transact(Stub.TRANSACTION_pause, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void sendMusicData(List<Song> songs) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeList(songs);
					mRemote.transact(Stub.TRANSACTION_sendMusicData, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void addOnMusicDataListener(OnMusicDataListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_addOnMusicDataListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void addOnCommandListener(OnCommandListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_addOnCammandListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void removeOnMusicDataListener(OnMusicDataListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_removeOnMusicDataListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void removeOnCommandListener(OnCommandListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_removeOnCammandListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void command(String cmd) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeString(cmd);
					mRemote.transact(Stub.TRANSACTION_command, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void addRegisterCommandListener(RegisterCommandListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_addRegisterCommandListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void removeRegisterCommandListener(RegisterCommandListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_removeRegisterCommandListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void registerCommand(String[] cmdNames, String cmd) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStringArray(cmdNames);
					_data.writeString(cmd);
					mRemote.transact(Stub.TRANSACTION_registerCommand, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void addOnDefaultNaviListener(kun.kt.opencam.ipc.OnDefaultNaviListener listener)
					throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_addOnDefaultNaviListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void removeOnDefaultNaviListener(kun.kt.opencam.ipc.OnDefaultNaviListener listener)
					throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_removeOnDefaultNaviListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void sendAirCmd(int cmdid, int[] params) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(cmdid);
					_data.writeIntArray(params);
					mRemote.transact(Stub.TRANSACTION_sendAirCmd, _data, _reply, 0);
					_reply.readException();
					_reply.readIntArray(params);
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void addOnAirSupportCmdsListener(OnAirSupportCmdsListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_addOnAirSupportCmdsListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void removeOnAirSupportCmdsListener(OnAirSupportCmdsListener listener) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeStrongBinder((((listener != null)) ? (listener.asBinder()) : (null)));
					mRemote.transact(Stub.TRANSACTION_removeOnAirSupportCmdsListener, _data, _reply, 0);
					_reply.readException();
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}

			@Override
			public void sendCmd(int module, int cmdid, int[] params) throws RemoteException {
				android.os.Parcel _data = android.os.Parcel.obtain();
				android.os.Parcel _reply = android.os.Parcel.obtain();
				try {
					_data.writeInterfaceToken(DESCRIPTOR);
					_data.writeInt(module);
					_data.writeInt(cmdid);
					_data.writeIntArray(params);
					mRemote.transact(Stub.TRANSACTION_sendCmd, _data, _reply, 0);
					_reply.readException();
					_reply.readIntArray(params);
				} finally {
					_reply.recycle();
					_data.recycle();
				}
			}
		}

		static final int TRANSACTION_openApp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
		static final int TRANSACTION_closeApp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
		static final int TRANSACTION_setRadioFreq = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
		static final int TRANSACTION_setOnBluetoothPhoneListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
		static final int TRANSACTION_setOnContactsListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
		static final int TRANSACTION_answer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
		static final int TRANSACTION_hang = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
		static final int TRANSACTION_dial = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
		static final int TRANSACTION_setOnMainChannleListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
		static final int TRANSACTION_setOnMachineStateListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
		static final int TRANSACTION_requestAudio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
		static final int TRANSACTION_releaseAudio = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
		static final int TRANSACTION_volumeUp = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
		static final int TRANSACTION_volumeDown = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
		static final int TRANSACTION_next = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
		static final int TRANSACTION_prev = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
		static final int TRANSACTION_play = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
		static final int TRANSACTION_pause = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
		static final int TRANSACTION_sendMusicData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
		static final int TRANSACTION_addOnMusicDataListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
		static final int TRANSACTION_addOnCammandListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
		static final int TRANSACTION_removeOnMusicDataListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
		static final int TRANSACTION_removeOnCammandListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
		static final int TRANSACTION_command = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
		static final int TRANSACTION_addRegisterCommandListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
		static final int TRANSACTION_removeRegisterCommandListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
		static final int TRANSACTION_registerCommand = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
		static final int TRANSACTION_addOnDefaultNaviListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
		static final int TRANSACTION_removeOnDefaultNaviListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 28);
		static final int TRANSACTION_sendAirCmd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 29);
		static final int TRANSACTION_addOnAirSupportCmdsListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 30);
		static final int TRANSACTION_removeOnAirSupportCmdsListener = (android.os.IBinder.FIRST_CALL_TRANSACTION + 31);
		static final int TRANSACTION_sendCmd = (android.os.IBinder.FIRST_CALL_TRANSACTION + 32);
	}

	// 打开应用
	public void openApp(String pkg) throws RemoteException;

	// 关闭应用
	public void closeApp(String pkg) throws RemoteException;

	// 设置收音机频率
	public void setRadioFreq(float freq) throws RemoteException;

	// 设置蓝牙状态监听
	public void setOnBluetoothPhoneListener(OnBluetoothPhoneListener listener) throws RemoteException;

	// 设置联系人更新监听
	public void setOnContactsListener(OnContactsListener listener) throws RemoteException;

	// 设置主状态监听
	public void setOnMainChannleListener(OnMainChannleListener listener) throws RemoteException;

	// 机器状态
	public void setOnMachineStateListener(OnMachineStateListener listener) throws RemoteException;

	// 接听
	public void answer() throws RemoteException;

	// 挂断
	public void hang() throws RemoteException;

	// 拨号
	public void dial(String number) throws RemoteException;

	// 请求音频独占
	public void requestAudio() throws RemoteException;

	// 释放音频独占
	public void releaseAudio() throws RemoteException;

	// 音量增大
	public void volumeUp() throws RemoteException;

	// 音量减小
	public void volumeDown() throws RemoteException;

	// 下一曲、下一台
	public void next() throws RemoteException;

	// 上一曲、上一台
	public void prev() throws RemoteException;

	// 播放
	public void play() throws RemoteException;

	// 暂停
	public void pause() throws RemoteException;

	// 发送命令
	public void command(String cmd) throws RemoteException;

	// 注册命令
	public void registerCommand(String[] cmdNames, String cmd) throws RemoteException;

	// 发送音乐列表
	public void sendMusicData(List<Song> songs) throws RemoteException;

	// 音乐列表数据监听
	public void addOnMusicDataListener(OnMusicDataListener listener) throws RemoteException;

	// 取消监听
	public void removeOnMusicDataListener(OnMusicDataListener listener) throws RemoteException;

	// 添加命令回调
	public void addOnCommandListener(OnCommandListener listener) throws RemoteException;

	// 取消监听
	public void removeOnCommandListener(OnCommandListener listener) throws RemoteException;

	// 添加命令回调
	public void addRegisterCommandListener(RegisterCommandListener listener) throws RemoteException;

	// 取消监听
	public void removeRegisterCommandListener(RegisterCommandListener listener) throws RemoteException;

	// 添加机器默认导航监听回调
	public void addOnDefaultNaviListener(OnDefaultNaviListener listener) throws RemoteException;

	// 取消机器默认导航监听
	public void removeOnDefaultNaviListener(OnDefaultNaviListener listener) throws RemoteException;

	// 添加机器默认导航监听回调
	public void addOnAirSupportCmdsListener(OnAirSupportCmdsListener listener) throws RemoteException;

	// 取消机器默认导航监听
	public void removeOnAirSupportCmdsListener(OnAirSupportCmdsListener listener) throws RemoteException;

	// 空调控制命令
	public void sendAirCmd(int cmdid, int[] params) throws RemoteException;

	// 空调控制命令
	public void sendCmd(int module, int cmdid, int[] params) throws RemoteException;
}
