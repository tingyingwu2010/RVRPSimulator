/**
 * Copyright © 2016 Thomas Mayer (thomas.mayer@unibw.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package vrpsim.r.util.api.model;

public class RConfig {

	private final String rFileName;

	private final String diagramMainTitel;
	private final String diagramTitelY;
	private final String diagramTitelX;
	private final boolean drawLegend;

	public RConfig(String rFileName, String diagramMainTitel, String diagramTitelY, String diagramTitelX, boolean drawLegend) {
		super();
		this.rFileName = rFileName;
		this.diagramMainTitel = diagramMainTitel;
		this.diagramTitelY = diagramTitelY;
		this.diagramTitelX = diagramTitelX;
		this.drawLegend = drawLegend;
	}

	public String getDiagramMainTitel() {
		return diagramMainTitel;
	}

	public String getDiagramTitelY() {
		return diagramTitelY;
	}

	public String getDiagramTitelX() {
		return diagramTitelX;
	}

	public String getrFileName() {
		return rFileName;
	}

	public boolean isDrawLegend() {
		return drawLegend;
	}

}
