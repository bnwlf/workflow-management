/*
 * Copyright (c) 2020 The Ontario Institute for Cancer Research. All rights reserved
 *
 * This program and the accompanying materials are made available under the terms of the GNU Affero General Public License v3.0.
 * You should have received a copy of the GNU Affero General Public License along with
 * this program. If not, see <http://www.gnu.org/licenses/>.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY
 * EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
 * INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED
 * TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS;
 * OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER
 * IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
 * ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.icgc.argo.workflow_management.util;

import java.io.IOException;
import java.util.Map;
import lombok.SneakyThrows;
import lombok.val;
import org.icgc.argo.workflow_management.secret.SecretProvider;
import org.icgc.argo.workflow_management.secret.impl.NoSecretProvider;
import org.icgc.argo.workflow_management.service.NextflowService;
import org.icgc.argo.workflow_management.service.NextflowWebLogEventSender;
import org.icgc.argo.workflow_management.service.model.RunParams;
import org.icgc.argo.workflow_management.service.properties.NextflowProperties;
import org.icgc.argo.workflow_management.wes.controller.model.WorkflowEngineParams;

public class WorkflowTest {

  private static final String weblogUrl = "http://localhost:8081";

  public static void main(String[] args) throws IOException {
    val p = new WorkflowEngineParams();
    val params =
        RunParams.builder()
            .workflowUrl("nextflow-io/hello")
            .workflowParams(Map.of())
            .workflowEngineParams(p)
            .build();
    runTest(params);
  }

  @SneakyThrows
  static void runTest(RunParams params) {
    NextflowProperties config = new NextflowProperties();
    SecretProvider secretProvider = new NoSecretProvider();
    NextflowWebLogEventSender webLogEventSender = new NextflowWebLogEventSender();
    val service = new NextflowService(config, secretProvider, webLogEventSender);
    val result = service.run(params);
    System.err.println(result.toString());
  }
}
