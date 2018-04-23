package cn.wonders.pos_qdg.tool8583;
import org.apache.commons.collections.map.ListOrderedMap;

/**
 * 用于存储每个域的信息
 */
public class Packet8583ConvertTable {
	// 存储每个域的详细信息
		private ListOrderedMap table = new ListOrderedMap();

		/**
		 *
		 * addField 添加域
		 * @param field
		 * void
		 * @exception
		 * @since  1.0.0
		 */
		public void addField(Packet8583Field field) {
			this.table.put(field.getFieldIndex(), field);
		}
	    /**
	     *
	     * clearTable 清除所有域的配置信息
	     * void
	     * @exception
	     * @since  1.0.0
	     */
		public void clearTable() {
			this.table.clear();
		}

		public ListOrderedMap getTable() {
			return table;
		}

		public void setTable(ListOrderedMap table) {
			this.table = table;
		}

		public Packet8583ConvertTable() {
			super();
		}
}
